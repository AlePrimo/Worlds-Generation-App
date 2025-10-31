package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactionMapperTest {

    private final FactionMapper mapper = new FactionMapper();

    @Test
    void toEntity_ok() {
        FactionRequestDTO dto = FactionRequestDTO.builder()
                .name("Orcs")
                .aggression(0.9)
                .expansionism(0.8)
                .size(300)
                .build();

        Faction entity = mapper.toEntity(dto);

        assertEquals("Orcs", entity.getName());
        assertEquals(0.9, entity.getAggression());
        assertEquals(0.8, entity.getExpansionism());
        assertEquals(300, entity.getSize());
    }

    @Test
    void toDTO_ok() {
        Faction faction = Faction.builder()
                .id(1L)
                .name("Humans")
                .aggression(0.4)
                .expansionism(0.6)
                .size(1000)
                .build();

        FactionResponseDTO dto = mapper.toDTO(faction);

        assertEquals(1L, dto.getId());
        assertEquals("Humans", dto.getName());
        assertEquals(0.4, dto.getAggression());
        assertEquals(0.6, dto.getExpansionism());
        assertEquals(1000, dto.getSize());
    }
}
