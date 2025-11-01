package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.dao.impl.EventDAOImpl;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventDAOImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventDAOImpl eventDAO;

    public EventDAOImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_delegatesToRepository() {
        Event event = new Event();
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventDAO.save(event);

        assertEquals(event, result);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void findAll_delegatesToRepository() {
        List<Event> events = List.of(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventDAO.findAll();

        assertEquals(events, result);
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void findById_found_returnsEntity() {
        Event event = new Event();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event result = eventDAO.findById(1L);

        assertEquals(event, result);
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void findById_notFound_returnsNull() {
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        Event result = eventDAO.findById(99L);

        assertNull(result);
        verify(eventRepository, times(1)).findById(99L);
    }

    @Test
    void delete_delegatesToRepository() {
        Event event = new Event();

        eventDAO.delete(event);

        verify(eventRepository, times(1)).delete(event);
    }
}
