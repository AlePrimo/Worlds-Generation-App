package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import java.util.List;

public interface EventService {
    EventResponseDTO create(EventRequestDTO dto);
    List<EventResponseDTO> getAll();
    EventResponseDTO getById(Long id);
}
