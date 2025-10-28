package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.dao.EventDAO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.Instant;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
void create_setsStartedAtAndActive() {
Event e = Event.builder().type("T").build();
when(eventDAO.save(any(Event.class))).thenAnswer(invocation -> {
Event arg = invocation.getArgument(0);
arg.setId(1L);
return arg;
});


Event saved = eventService.create(e);
assertNotNull(saved.getStartedAt());
assertTrue(saved.isActive());
assertEquals(1L, saved.getId());
}
}