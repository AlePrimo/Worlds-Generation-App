package com.primo.worldgen_backend.dto.factions;

import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactionRequestDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        FactionRequestDTO dto = new FactionRequestDTO();
        dto.setName("Elfos");
        dto.setAggression(0.4);
        dto.setExpansionism(0.6);
        dto.setSize(300);

        assertEquals("Elfos", dto.getName());
        assertEquals(0.4, dto.getAggression());
        assertEquals(0.6, dto.getExpansionism());
        assertEquals(300, dto.getSize());

        FactionRequestDTO built = FactionRequestDTO.builder()
                .name("Orcos")
                .aggression(0.9)
                .expansionism(0.8)
                .size(500)
                .build();

        assertEquals("Orcos", built.getName());
        assertEquals(0.9, built.getAggression());

        FactionRequestDTO allArgs = new FactionRequestDTO("Humanos", 0.3, 0.5, 1000);
        assertEquals("Humanos", allArgs.getName());
        assertEquals(1000, allArgs.getSize());
    }
}
