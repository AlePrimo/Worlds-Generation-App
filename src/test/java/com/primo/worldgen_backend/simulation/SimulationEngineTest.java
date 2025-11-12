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
import java.time.Instant;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SimulationEngineTest {

    @Mock
    private WorldService worldService;

    @Mock
    private RegionService regionService;

    @Mock
    private EventService eventService;

    @Mock
    private WorldEventPublisher worldPublisher;

    @Mock
    private RegionEventPublisher regionPublisher;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private RegionMapper regionMapper;

    @InjectMocks
    private SimulationEngine engine;

    @BeforeEach
    void setUp() throws Exception {
        // nothing here for now; random will be set per-test if needed
    }

    @Test
    void tickSimulation_incrementsWorldTicks_andPublishesTick() throws Exception {
        World w = World.builder().id(1L).name("W").ticks(0).createdAt(Instant.now()).build();

        when(worldService.findAll()).thenReturn(List.of(w));
        when(regionService.findAll()).thenReturn(List.of()); // no regions -> only world path executed

        // inject a Random that does not affect anything (won't be used for regions)
        setDeterministicRandom(0.5, 0);

        engine.tickSimulation();

        // verify world tick increment and persistence + publish
        ArgumentCaptor<World> capt = ArgumentCaptor.forClass(World.class);
        verify(worldService, times(1)).update(eq(1L), capt.capture());
        World updated = capt.getValue();
        assertEquals(1L, updated.getId());
        assertEquals(1, updated.getTicks()); // ticks incremented from 0 -> 1

        verify(worldPublisher, times(1)).publishWorldTick(eq(1L), eq(1L));
    }

    @Test
    void tickSimulation_generatesEvent_appliesEffects_andPublishesForRegion() throws Exception {
        // Setup a region with initial values (no events)
        Region region = Region.builder()
                .id(11L)
                .name("R")
                .population(1000)
                .water(80.0)
                .food(80.0)
                .minerals(100.0)
                .events(null)
                .build();

        when(worldService.findAll()).thenReturn(List.of()); // no worlds for this test
        when(regionService.findAll()).thenReturn(List.of(region));

        // Make Random deterministic: nextDouble -> 0.0 (force event), nextInt -> 0 (pick first EVENT_TYPES -> SEQUIA)
        setDeterministicRandom(0.0, 0);

        // Prepare the Event that the service will "save" and mapper will map
        Event savedEvent = Event.builder()
                .id(999L)
                .type("SEQUIA")
                .startedAt(Instant.now())
                .description("Auto-generated event: SEQUIA")
                .severity(5)
                .active(true)
                .region(region)
                .build();

        when(eventService.create(any(Event.class))).thenReturn(savedEvent);

        EventResponseDTO eventDto = new EventResponseDTO();
        when(eventMapper.toDTO(savedEvent)).thenReturn(eventDto);

        RegionResponseDTO regionDto = RegionResponseDTO.builder().id(region.getId()).name(region.getName()).build();
        when(regionMapper.toDTO(any(Region.class))).thenReturn(regionDto);

        // Run tickSimulation -> should generate event, apply effects, update region and publish
        engine.tickSimulation();

        // verify event created
        verify(eventService, times(1)).create(any(Event.class));
        verify(regionPublisher, times(1)).publishRegionEvent(eq(region.getId()), eq(eventDto));

        // After a "SEQUIA" of severity 5:
        // factor = 5/10 = 0.5
        // water reduction = 15 * 0.5 = 7.5 => 80 - 7.5 = 72.5
        // food reduction = 10 * 0.5 = 5 => 80 - 5 = 75
        assertTrue(region.getWater() <= 80.0 && region.getWater() >= 72.4 && region.getFood() <= 80.0 && region.getFood() >= 74.9);

        // verify region persisted/updated and published
        verify(regionService, times(1)).update(eq(region.getId()), eq(region));
        verify(regionPublisher, times(1)).publishRegionUpdate(eq(region.getId()), eq(regionDto));
    }

    // ---- helpers ----

    /**
     * Replaces the private final Random "random" field inside engine
     * with a deterministic Random-like object whose nextDouble()/nextInt(bound)
     * return the given values. This uses reflection to set the private field.
     */
    private void setDeterministicRandom(double nextDoubleValue, int nextIntValue) throws Exception {
        Random deterministic = new Random() {
            @Override
            public double nextDouble() {
                return nextDoubleValue;
            }

            @Override
            public int nextInt(int bound) {
                // return value bounded to avoid IllegalArgumentException
                if (bound <= 0) return 0;
                return Math.min(Math.abs(nextIntValue), bound - 1);
            }
        };

        Field f = SimulationEngine.class.getDeclaredField("random");
        f.setAccessible(true);

        // Remove final-like modifier by using set (works in tests) — assign new Random instance
        f.set(engine, deterministic);
    }
}
