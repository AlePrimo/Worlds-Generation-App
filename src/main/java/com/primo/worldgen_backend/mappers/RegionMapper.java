package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RegionMapper {

    public Region toEntity(RegionRequestDTO dto) {
        return Region.builder()
                .name(dto.getName())
                .lat(dto.getLat())
                .lon(dto.getLon())
                .population(dto.getPopulation())
                .water(dto.getWater())
                .food(dto.getFood())
                .minerals(dto.getMinerals())
                .alive(dto.isAlive())
                .build();
    }

    public RegionResponseDTO toDTO(Region entity) {
        return RegionResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .population(entity.getPopulation())
                .water(entity.getWater())
                .food(entity.getFood())
                .minerals(entity.getMinerals())
                .alive(entity.isAlive())
                .factionIds(entity.getFactions() != null ?
                        entity.getFactions().stream().map(Faction::getId).collect(Collectors.toList())
                        : null)
                .eventIds(entity.getEvents() != null ?
                        entity.getEvents().stream().map(Event::getId).collect(Collectors.toList())
                        : null)
                .build();
    }
}

