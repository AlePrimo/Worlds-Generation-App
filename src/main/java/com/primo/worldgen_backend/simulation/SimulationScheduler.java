package com.primo.worldgen_backend.simulation;

import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.EventMapper;
import com.primo.worldgen_backend.mappers.WorldMapper;
import com.primo.worldgen_backend.messaging.RegionEventPublisher;
import com.primo.worldgen_backend.messaging.WorldEventPublisher;
import com.primo.worldgen_backend.service.EventService;
import com.primo.worldgen_backend.service.RegionService;
import com.primo.worldgen_backend.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SimulationScheduler {

    private final WorldService worldService;
    private final WorldMapper worldMapper;
    private final WorldEventPublisher worldPublisher;

    private final RegionService regionService;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final RegionEventPublisher regionPublisher;

    private final Random random = new Random();


    private static final double EVENT_PROBABILITY = 0.15;


    private static final int MAX_ACTIVE_EVENTS_PER_REGION = 5;

    private static final String[] EVENT_TYPES = new String[] {
            "SEQUIA", "INUNDACION", "EPIDEMIA", "TERREMOTO", "ERUPCION", "PLAGA"
    };

    @Scheduled(fixedRate = 5000)
    public void tickAllWorlds() {

        List<World> worlds = worldService.findAll();
        for (World w : worlds) {
            w.setTicks(w.getTicks() + 1);
            worldService.update(w.getId(), w);
        }


        List<Region> regions = regionService.findAll();
        for (Region region : regions) {

            List<Event> activeEvents = region.getEvents() == null ? List.of()
                    : region.getEvents().stream().filter(Event::isActive).collect(Collectors.toList());

            if (activeEvents.size() >= MAX_ACTIVE_EVENTS_PER_REGION) {
                continue;
            }


            if (random.nextDouble() > EVENT_PROBABILITY) {
                continue;
            }


            String type = pickRandomEventType();
            int severity = 1 + random.nextInt(10); // 1..10

            Event evt = Event.builder()
                    .type(type)
                    .startedAt(Instant.now())
                    .description("Auto-generated event: " + type)
                    .severity(severity)
                    .active(true)
                    .region(region)
                    .build();

            Event saved = eventService.create(evt);

            
            EventResponseDTO dto = eventMapper.toDTO(saved);
            regionPublisher.publishRegionEvent(region.getId(), dto);
        }
    }

    private String pickRandomEventType() {
        return EVENT_TYPES[random.nextInt(EVENT_TYPES.length)];
    }
}
