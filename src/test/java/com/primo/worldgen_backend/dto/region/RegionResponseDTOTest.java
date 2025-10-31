package com.primo.worldgen_backend.dto.region;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RegionResponseDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(1L);
        dto.setName("Montañas");
        dto.setLat(1.1);
        dto.setLon(2.2);
        dto.setPopulation(300);
        dto.setWater(60.0);
        dto.setFood(70.0);
        dto.setMinerals(80.0);
        dto.setAlive(true);
        dto.setFactionIds(List.of(10L, 20L));
        dto.setEventIds(List.of(30L, 40L));

        assertEquals("Montañas", dto.getName());
        assertEquals(List.of(10L, 20L), dto.getFactionIds());

        RegionResponseDTO built = RegionResponseDTO.builder()
                .id(2L)
                .name("Llanuras")
                .lat(9.9)
                .lon(8.8)
                .population(150)
                .water(40.0)
                .food(60.0)
                .minerals(20.0)
                .alive(false)
                .factionIds(List.of(5L))
                .eventIds(List.of(6L))
                .build();

        assertEquals("Llanuras", built.getName());
        assertFalse(built.isAlive());

        RegionResponseDTO allArgs = new RegionResponseDTO(
                3L, "Valle", 3.3, 4.4, 100, 10.0, 20.0, 30.0, true,
                List.of(9L), List.of(99L)
        );
        assertEquals("Valle", allArgs.getName());
    }
}
