package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.EventDAO;
import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.mapper.EventMapper;
import com.primo.worldgen_backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventDAO eventDAO;
    private final FactionDAO factionDAO;

    @Override
    public EventResponseDTO create(EventRequestDTO dto){
        Faction faction = factionDAO.findById(dto.getFactionId());
        Event event = EventMapper.toEntity(dto, faction);
        return EventMapper.toDTO(eventDAO.save(event));
    }

    @Override
    public List<EventResponseDTO> getAll(){
        return eventDAO.findAll().stream().map(EventMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO getById(Long id){
        Event e = eventDAO.findById(id);
        return e!=null?EventMapper.toDTO(e):null;
    }
}
