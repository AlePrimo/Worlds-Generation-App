package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.EventDAO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    @Override
    public Event create(Event event) {
        if (event.getStartedAt() == null) {
            event.setStartedAt(Instant.now());
        }
        event.setActive(true);
        return eventDAO.save(event);
    }

    @Override
    public Event update(Long id, Event event) {
        Event existing = findById(id);
        existing.setType(event.getType());
        existing.setStartedAt(event.getStartedAt());
        existing.setDescription(event.getDescription());
        existing.setSeverity(event.getSeverity());
        existing.setActive(event.isActive());
        return eventDAO.save(existing);
    }

    @Override
    public Event findById(Long id) {
        Event event = eventDAO.findById(id);
        if (event == null) {
            throw new RuntimeException("Event not found with id " + id);
        }
        return event;
    }

    @Override
    public List<Event> findAll() {
        return eventDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        Event existing = findById(id);
        eventDAO.delete(existing);
    }
}

