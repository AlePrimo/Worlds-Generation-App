package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.EventRequestDTO;
import com.primo.worldgen_backend.dto.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Faction;

public class EventMapper {

    public static Event toEntity(EventRequestDTO dto, Faction faction){
        return Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .impact(dto.getImpact())
                .faction(faction)
                .build();
    }

    public static EventResponseDTO toDTO(Event event){
        return EventResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .impact(event.getImpact())
                .factionId(event.getFaction()!=null ? event.getFaction().getId() : null)
                .build();
    }
}
