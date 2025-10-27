package com.primo.worldgen_backend.mappers;


import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;

public class FactionMapper {

    public static Faction toEntity(FactionRequestDTO dto, Region region){
        return Faction.builder()
                .name(dto.getName())
                .power(dto.getPower())
                .region(region)
                .build();
    }

    public static FactionResponseDTO toDTO(Faction faction){
        return FactionResponseDTO.builder()
                .id(faction.getId())
                .name(faction.getName())
                .power(faction.getPower())
                .regionId(faction.getRegion()!=null ? faction.getRegion().getId() : null)
                .build();
    }
}
