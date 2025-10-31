package com.primo.worldgen_backend.dto.regions;

import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegionRequestDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        RegionRequestDTO dto = new RegionRequestDTO();
        dto.setName("Bosque Norte");
        dto.setLat(12.3);
        dto.setLon(45.6);
        dto.setPopulation(1000);
        dto.setWater(50.0);
        dto.setFood(70.0);
        dto.setMinerals(20.0);
        dto.setAlive(true);

        assertEquals("Bosque Norte", dto.getName());
        assertTrue(dto.isAlive());

        RegionRequestDTO built = RegionRequestDTO.builder()
                .name("Desierto Sur")
                .lat(-34.5)
                .lon(12.3)
                .population(200)
                .water(5.0)
                .food(10.0)
                .minerals(3.0)
                .alive(false)
                .build();

        assertEquals("Desierto Sur", built.getName());
        assertFalse(built.isAlive());

        RegionRequestDTO allArgs = new RegionRequestDTO("Páramo", 0.1, 0.2, 50, 10.0, 15.0, 5.0, true);
        assertEquals("Páramo", allArgs.getName());
        assertTrue(allArgs.isAlive());
    }
}
