package com.primo.worldgen_backend.dto.world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldRequestDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        WorldRequestDTO dto = new WorldRequestDTO();
        dto.setName("Gaia");
        assertEquals("Gaia", dto.getName());

        WorldRequestDTO built = WorldRequestDTO.builder().name("Terra").build();
        assertEquals("Terra", built.getName());

        WorldRequestDTO allArgs = new WorldRequestDTO("Marte");
        assertEquals("Marte", allArgs.getName());
    }
}
