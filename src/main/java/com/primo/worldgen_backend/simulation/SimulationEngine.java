package com.primo.worldgen_backend.simulation;

import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.EventMapper;
import com.primo.worldgen_backend.messaging.RegionEventPublisher;
import com.primo.worldgen_backend.messaging.WorldEventPublisher;
import com.primo.worldgen_backend.service.EventService;
import com.primo.worldgen_backend.service.RegionService;
import com.primo.worldgen_backend.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimulationEngine {

    private final WorldService worldService;
    private final RegionService regionService;
    private final EventService eventService;

    private final WorldEventPublisher worldPublisher;
    private final RegionEventPublisher regionPublisher;
    private final EventMapper eventMapper;

    private final Random random = new Random();

    private static final double EVENT_PROBABILITY = 0.15;
    private static final int MAX_ACTIVE_EVENTS_PER_REGION = 5;
    private static final String[] EVENT_TYPES = {
            "SEQUIA", "INUNDACION", "EPIDEMIA", "TERREMOTO", "ERUPCION", "PLAGA"
    };

    public void tickSimulation() {

        // 1️⃣ Avanzar el tiempo en todos los mundos
        List<World> worlds = worldService.findAll();
        for (World world : worlds) {
            world.setTicks(world.getTicks() + 1);
            worldService.update(world.getId(), world);
            worldPublisher.publishWorldTick(world.getId(), world.getTicks());
        }

        // 2️⃣ Procesar regiones
        List<Region> regions = regionService.findAll();
        for (Region region : regions) {
            simulateRegion(region);
        }
    }

    private void simulateRegion(Region region) {
        // Obtener eventos activos
        List<Event> activeEvents = region.getEvents() == null ? List.of()
                : region.getEvents().stream().filter(Event::isActive).collect(Collectors.toList());

        // Límite de eventos activos
        if (activeEvents.size() >= MAX_ACTIVE_EVENTS_PER_REGION) return;

        // Probabilidad de evento
        if (random.nextDouble() > EVENT_PROBABILITY) return;

        // Crear un nuevo evento aleatorio
        String type = EVENT_TYPES[random.nextInt(EVENT_TYPES.length)];
        int severity = 1 + random.nextInt(10);

        Event newEvent = Event.builder()
                .type(type)
                .startedAt(Instant.now())
                .description("Evento automático generado: " + type)
                .severity(severity)
                .active(true)
                .region(region)
                .build();

        Event saved = eventService.create(newEvent);
        EventResponseDTO eventDTO = eventMapper.toDTO(saved);

        regionPublisher.publishRegionEvent(region.getId(), eventDTO);
    }
}
