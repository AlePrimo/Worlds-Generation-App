package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import org.springframework.stereotype.Component;


@Component
public class EventMapper {



    public  Event toEntity(EventRequestDTO dto) {
        return Event.builder()
                .type(dto.getType())
                .startedAt(dto.getStartedAt())
                .description(dto.getDescription())
                .severity(dto.getSeverity())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
    }

    public  EventResponseDTO toDTO(Event event) {
        return EventResponseDTO.builder()
                .id(event.getId())
                .type(event.getType())
                .startedAt(event.getStartedAt())
                .description(event.getDescription())
                .severity(event.getSeverity())
                .active(event.isActive())
                .build();
    }
}
