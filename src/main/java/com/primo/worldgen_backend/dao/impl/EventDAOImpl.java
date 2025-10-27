package com.primo.worldgen_backend.dao.impl;

import com.primo.worldgen_backend.dao.EventDAO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventDAOImpl implements EventDAO {

    private final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}
