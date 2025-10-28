package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dao.EventDAO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;

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
    void update_existing_updatesFields() {
        Event existing = Event.builder().id(1L).type("A").startedAt(Instant.now()).description("d").severity(3).active(true).build();
        Event update = Event.builder().type("B").startedAt(Instant.now()).description("d2").severity(4).active(false).build();

        when(eventDAO.findById(1L)).thenReturn(existing);
        when(eventDAO.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event res = eventService.update(1L, update);
        assertEquals("B", res.getType());
        assertEquals(4, res.getSeverity());
    }

    @Test
    void delete_existing_deletes() {
        Event existing = Event.builder().id(2L).type("X").build();
        when(eventDAO.findById(2L)).thenReturn(existing);
        doNothing().when(eventDAO).delete(existing);

        eventService.delete(2L);
        verify(eventDAO, times(1)).delete(existing);
    }

    @Test
    void findById_notFound_throws() {
        when(eventDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> eventService.findById(99L));
        assertTrue(ex.getMessage().contains("Event not found"));
    }
}
