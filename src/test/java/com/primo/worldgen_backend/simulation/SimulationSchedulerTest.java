package com.primo.worldgen_backend.simulation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SimulationSchedulerTest {

    @Mock
    private SimulationEngine simulationEngine;

    @InjectMocks
    private SimulationScheduler scheduler;

    @Test
    void tick_delegatesToSimulationEngine() {
        scheduler.tick();
        verify(simulationEngine, times(1)).tickSimulation();
    }
}
