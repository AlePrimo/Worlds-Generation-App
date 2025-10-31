package com.primo.worldgen_backend.dto.faction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactionResponseDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        FactionResponseDTO dto = new FactionResponseDTO();
        dto.setId(1L);
        dto.setName("Elfos Oscuros");
        dto.setAggression(0.8);
        dto.setExpansionism(0.9);
        dto.setSize(800);

        assertEquals(1L, dto.getId());
        assertEquals("Elfos Oscuros", dto.getName());
        assertEquals(0.8, dto.getAggression());
        assertEquals(0.9, dto.getExpansionism());
        assertEquals(800, dto.getSize());

        FactionResponseDTO built = FactionResponseDTO.builder()
                .id(2L)
                .name("Humanos")
                .aggression(0.2)
                .expansionism(0.4)
                .size(1200)
                .build();

        assertEquals("Humanos", built.getName());
        assertEquals(1200, built.getSize());

        FactionResponseDTO allArgs = new FactionResponseDTO(3L, "Orcos", 1.0, 1.0, 900);
        assertEquals("Orcos", allArgs.getName());
        assertEquals(900, allArgs.getSize());
    }
}
