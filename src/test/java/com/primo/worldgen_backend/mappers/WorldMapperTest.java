package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.world.WorldRequestDTO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import com.primo.worldgen_backend.entities.World;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapperTest {

    private final WorldMapper mapper = new WorldMapper();

    @Test
    void toEntity_ok() {
        WorldRequestDTO dto = WorldRequestDTO.builder()
                .name("Gaia")
                .build();

        World entity = mapper.toEntity(dto);

        assertEquals("Gaia", entity.getName());
    }

    @Test
    void toDTO_ok() {
        Instant now = Instant.now();
        World world = World.builder()
                .id(42L)
                .name("Terra")
                .ticks(100)
                .createdAt(now)
                .build();

        WorldResponseDTO dto = mapper.toDTO(world);

        assertEquals(42L, dto.getId());
        assertEquals("Terra", dto.getName());
        assertEquals(100, dto.getTicks());
        assertEquals(now, dto.getCreatedAt());
    }
}
