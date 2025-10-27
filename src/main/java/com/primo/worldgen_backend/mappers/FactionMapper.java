package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;
import org.springframework.stereotype.Component;

@Component
public class FactionMapper {

    public  Faction toEntity(FactionRequestDTO dto) {
        return Faction.builder()
                .name(dto.getName())
                .aggression(dto.getAggression())
                .expansionism(dto.getExpansionism())
                .size(dto.getSize())
                .build();
    }

    public  FactionResponseDTO toDTO(Faction faction) {
        return FactionResponseDTO.builder()
                .id(faction.getId())
                .name(faction.getName())
                .aggression(faction.getAggression())
                .expansionism(faction.getExpansionism())
                .size(faction.getSize())
                .build();
    }
}
