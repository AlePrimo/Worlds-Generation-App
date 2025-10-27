package com.primo.worldgen_backend.mappers;


import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.entities.World;

public class RegionMapper {

    public static Region toEntity(RegionRequestDTO dto, World world){
        return Region.builder()
                .name(dto.getName())
                .world(world)
                .resources(dto.getResources())
                .build();
    }

    public static RegionResponseDTO toDTO(Region region){
        return RegionResponseDTO.builder()
                .id(region.getId())
                .name(region.getName())
                .worldId(region.getWorld()!=null ? region.getWorld().getId() : null)
                .resources(region.)
                .build();
    }
}
