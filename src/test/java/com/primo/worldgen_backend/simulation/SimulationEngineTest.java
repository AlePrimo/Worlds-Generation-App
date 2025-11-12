package com.primo.worldgen_backend.simulation;

import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.EventMapper;
import com.primo.worldgen_backend.mappers.RegionMapper;
import com.primo.worldgen_backend.messaging.RegionEventPublisher;
import com.primo.worldgen_backend.messaging.WorldEventPublisher;
import com.primo.worldgen_backend.service.EventService;
import com.primo.worldgen_backend.service.RegionService;
import com.primo.worldgen_backend.service.WorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SimulationEngineTest {

    @Mock private WorldService worldService;
    @Mock private RegionService regionService;
    @Mock private EventService eventService;
    @Mock private WorldEventPublisher worldPublisher;
    @Mock private RegionEventPublisher regionPublisher;
    @Mock private EventMapper eventMapper;
    @Mock private RegionMapper regionMapper;

    @InjectMocks
    private SimulationEngine engine;

    @BeforeEach
    void setUp() {}

    @Test
    void tickSimulation_incrementsWorldTicks_andPublishesTick() throws Exception {
        World w = World.builder().id(1L).name("W").ticks(0).createdAt(Instant.now()).build();
        when(worldService.findAll()).thenReturn(List.of(w));
        when(regionService.findAll()).thenReturn(List.of());

        setDeterministicRandom(0.5, 0);
        engine.tickSimulation();

        ArgumentCaptor<World> capt = ArgumentCaptor.forClass(World.class);
        verify(worldService).update(eq(1L), capt.capture());
        assertEquals(1, capt.getValue().getTicks());
        verify(worldPublisher).publishWorldTick(eq(1L), eq(1L));
    }

    @Test
    void tickSimulation_generatesEvent_appliesEffects_andPublishesForRegion() throws Exception {
        Region region = Region.builder()
                .id(11L).name("R")
                .population(1000)
                .water(80.0).food(80.0).minerals(100.0)
                .events(null)
                .build();

        when(worldService.findAll()).thenReturn(List.of());
        when(regionService.findAll()).thenReturn(List.of(region));

        setDeterministicRandom(0.0, 0); // fuerza SEQUIA
        Event savedEvent = Event.builder()
                .id(999L).type("SEQUIA").severity(5).active(true).region(region)
                .build();

        when(eventService.create(any(Event.class))).thenReturn(savedEvent);
        when(eventMapper.toDTO(savedEvent)).thenReturn(new EventResponseDTO());
        when(regionMapper.toDTO(any())).thenReturn(
                RegionResponseDTO.builder().id(region.getId()).name(region.getName()).build()
        );

        engine.tickSimulation();

        verify(eventService).create(any(Event.class));
        verify(regionService).update(eq(region.getId()), eq(region));
        verify(regionPublisher).publishRegionEvent(eq(region.getId()), any());
        verify(regionPublisher).publishRegionUpdate(eq(region.getId()), any());
    }

    @Test
    void simulateRegion_doesNothingWhenTooManyActiveEvents() throws Exception {
        Region region = Region.builder().id(2L).events(List.of(
                Event.builder().active(true).build(),
                Event.builder().active(true).build(),
                Event.builder().active(true).build(),
                Event.builder().active(true).build(),
                Event.builder().active(true).build()
        )).build();

        when(worldService.findAll()).thenReturn(List.of());
        when(regionService.findAll()).thenReturn(List.of(region));

        setDeterministicRandom(0.0, 0);
        engine.tickSimulation();

        verifyNoInteractions(eventService);
    }

    @Test
    void simulateRegion_doesNothingWhenRandomProbabilityFails() throws Exception {
        Region region = Region.builder().id(3L).events(List.of()).build();

        when(worldService.findAll()).thenReturn(List.of());
        when(regionService.findAll()).thenReturn(List.of(region));

        setDeterministicRandom(0.99, 0);
        engine.tickSimulation();

        verifyNoInteractions(eventService);
    }

    @Test
    void applyEventEffects_coversAllBranches() throws Exception {
        Region r = Region.builder()
                .water(50.0).food(50.0).minerals(50.0).population(100)
                .build();

        Method m = SimulationEngine.class.getDeclaredMethod(
                "applyEventEffects", Region.class, String.class, int.class);
        m.setAccessible(true);

        // Cubre los 6 casos del switch
        m.invoke(engine, r, "SEQUIA", 10);
        m.invoke(engine, r, "INUNDACION", 10);
        m.invoke(engine, r, "EPIDEMIA", 10);
        m.invoke(engine, r, "TERREMOTO", 10);
        m.invoke(engine, r, "ERUPCION", 10);
        m.invoke(engine, r, "PLAGA", 10);

        // Valores válidos post-aplicación
        assertTrue(r.getWater() >= 0);
        assertTrue(r.getFood() >= 0);
        assertTrue(r.getPopulation() >= 0);
    }

    /** helper para reemplazar Random con valores deterministas */
    private void setDeterministicRandom(double nextDoubleValue, int nextIntValue) throws Exception {
        Random deterministic = new Random() {
            @Override public double nextDouble() { return nextDoubleValue; }
            @Override public int nextInt(int bound) {
                return (bound <= 0) ? 0 : Math.min(Math.abs(nextIntValue), bound - 1);
            }
        };
        Field f = SimulationEngine.class.getDeclaredField("random");
        f.setAccessible(true);
        f.set(engine, deterministic);
    }
}
