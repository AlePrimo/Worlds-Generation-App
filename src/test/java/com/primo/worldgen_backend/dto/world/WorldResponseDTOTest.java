package com.primo.worldgen_backend.dto.world;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WorldResponseDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        Instant now = Instant.now();

        WorldResponseDTO dto = new WorldResponseDTO();
        dto.setId(1L);
        dto.setName("Gaia");
        dto.setTicks(5000);
        dto.setCreatedAt(now);
        dto.setRegionIds(List.of(1L, 2L));

        assertEquals(1L, dto.getId());
        assertEquals("Gaia", dto.getName());
        assertEquals(5000, dto.getTicks());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(List.of(1L, 2L), dto.getRegionIds());

        WorldResponseDTO built = WorldResponseDTO.builder()
                .id(2L)
                .name("Terra")
                .ticks(999)
                .createdAt(now)
                .regionIds(List.of(3L))
                .build();

        assertEquals("Terra", built.getName());

        WorldResponseDTO allArgs = new WorldResponseDTO(3L, "Marte", 123, now, List.of(4L, 5L));
        assertEquals("Marte", allArgs.getName());
        assertEquals(List.of(4L, 5L), allArgs.getRegionIds());
    }
}
