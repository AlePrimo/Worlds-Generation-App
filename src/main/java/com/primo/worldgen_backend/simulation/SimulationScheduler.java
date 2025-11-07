package com.primo.worldgen_backend.simulation;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimulationScheduler {

    private final SimulationEngine simulationEngine;

    @Scheduled(fixedRate = 5000)
    public void tick() {
        simulationEngine.tickSimulation();
    }
}
