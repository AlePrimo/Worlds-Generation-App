package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.entities.Event;
import java.util.List;

public interface EventDAO {
    Event save(Event event);
    List<Event> findAll();
    Event findById(Long id);
    void delete(Event event);
}
