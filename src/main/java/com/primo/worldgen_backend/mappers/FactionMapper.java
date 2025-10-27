package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;

public class FactionMapper {

    public static Faction toEntity(FactionRequestDTO dto) {
        return Faction.builder()
                .name(dto.getName())
                .aggression(dto.getAggression())
                .expansionism(dto.getExpansionism())
                .size(dto.getSize())
                .build();
    }

    public static FactionResponseDTO toDTO(Faction faction) {
        return FactionResponseDTO.builder()
                .id(faction.getId())
                .name(faction.getName())
                .aggression(faction.getAggression())
                .expansionism(faction.getExpansionism())
                .size(faction.getSize())
                .build();
    }
}
