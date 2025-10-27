package com.primo.worldgen_backend.service.impl;


import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.repository.EventRepository;
import com.primo.worldgen_backend.repository.RegionRepository;
import com.primo.worldgen_backend.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final RegionRepository regionRepository;

    public EventServiceImpl(EventRepository eventRepository, RegionRepository regionRepository) {
        this.eventRepository = eventRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public Event create(Event event) {
        validateRegion(event.getRegion().getId());
        return eventRepository.save(event);
    }

    @Override
    public Event update(Long id, Event event) {
        Event existing = findById(id);
        validateRegion(event.getRegion().getId());
        event.setId(existing.getId());
        return eventRepository.save(event);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Event existing = findById(id);
        eventRepository.delete(existing);
    }

    private void validateRegion(Long regionId) {
        regionRepository.findById(regionId)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + regionId));
    }
}

