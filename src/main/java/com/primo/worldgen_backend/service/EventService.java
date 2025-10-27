package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.entities.Event;

import java.util.List;

public interface EventService {
    Event create(Event event);
    Event update(Long id, Event event);
    Event findById(Long id);
    List<Event> findAll();
    void delete(Long id);
}
