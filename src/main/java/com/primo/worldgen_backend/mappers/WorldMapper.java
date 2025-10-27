package com.primo.worldgen_backend.mappers;


import com.primo.worldgen_backend.dto.world.WorldRequestDTO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import com.primo.worldgen_backend.entities.World;
import org.springframework.stereotype.Component;

@Component
public class WorldMapper {

    public  World toEntity(WorldRequestDTO dto){
        return World.builder()
                .name(dto.getName())
                .build();
    }

    public  WorldResponseDTO toDTO(World world){
        return WorldResponseDTO.builder()
                .id(world.getId())
                .name(world.getName())
                .ticks(world.getTicks())
                .createdAt(world.getCreatedAt())
                .build();
    }
}
