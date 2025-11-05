package com.primo.worldgen_backend.simulation;

import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.WorldMapper;
import com.primo.worldgen_backend.messaging.WorldEventPublisher;
import com.primo.worldgen_backend.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimulationScheduler {

    private final WorldService worldService;
    private final WorldMapper worldMapper;
    private final WorldEventPublisher publisher;


    @Scheduled(fixedRate = 5000)
    public void tickAllWorlds() {
        var worlds = worldService.findAll();
        for (World w : worlds) {
          
            w.setTicks(w.getTicks() + 1);
            worldService.update(w.getId(), w); // this will persist and publish via service
        }
    }
}
