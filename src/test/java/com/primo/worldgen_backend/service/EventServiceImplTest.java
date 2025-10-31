package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dao.EventDAO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    @Mock
    private EventDAO eventDAO;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_withNullStartedAt_setsNowAndActiveTrue() {
        Event event = Event.builder().id(1L).type("A").startedAt(null).build();
        when(eventDAO.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event result = eventService.create(event);

        assertTrue(result.isActive());
        assertNotNull(result.getStartedAt());
        verify(eventDAO).save(event);
    }

    @Test
    void create_withStartedAt_keepsValue() {
        Instant now = Instant.now();
        Event event = Event.builder().startedAt(now).build();
        when(eventDAO.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event result = eventService.create(event);

        assertEquals(now, result.getStartedAt());
        assertTrue(result.isActive());
    }

    @Test
    void update_existing_updatesAllFields() {
        Event existing = Event.builder().id(1L).type("Old").build();
        Event update = Event.builder().type("New").startedAt(Instant.now()).description("desc").severity(5).active(false).build();

        when(eventDAO.findById(1L)).thenReturn(existing);
        when(eventDAO.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event result = eventService.update(1L, update);

        assertEquals("New", result.getType());
        assertEquals("desc", result.getDescription());
        assertFalse(result.isActive());
        verify(eventDAO).save(existing);
    }

    @Test
    void findById_found_returnsEvent() {
        Event event = Event.builder().id(1L).type("X").build();
        when(eventDAO.findById(1L)).thenReturn(event);

        Event result = eventService.findById(1L);

        assertEquals("X", result.getType());
        verify(eventDAO).findById(1L);
    }

    @Test
    void findById_notFound_throws() {
        when(eventDAO.findById(1L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> eventService.findById(1L));
        assertTrue(ex.getMessage().contains("Event not found"));
    }

    @Test
    void findAll_returnsList() {
        when(eventDAO.findAll()).thenReturn(List.of(new Event(), new Event()));

        List<Event> list = eventService.findAll();

        assertEquals(2, list.size());
        verify(eventDAO).findAll();
    }

    @Test
    void delete_existing_deletes() {
        Event existing = Event.builder().id(2L).build();
        when(eventDAO.findById(2L)).thenReturn(existing);
        doNothing().when(eventDAO).delete(existing);

        eventService.delete(2L);

        verify(eventDAO).delete(existing);
    }
}
