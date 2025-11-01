package com.primo.worldgen_backend.entities;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Event event = new Event();
        Instant now = Instant.now();

        event.setId(1L);
        event.setType("SEQUIA");
        event.setStartedAt(now);
        event.setDescription("Sequía prolongada");
        event.setSeverity(7);
        event.setActive(false);

        assertEquals(1L, event.getId());
        assertEquals("SEQUIA", event.getType());
        assertEquals(now, event.getStartedAt());
        assertEquals("Sequía prolongada", event.getDescription());
        assertEquals(7, event.getSeverity());
        assertFalse(event.isActive());
    }

    @Test
    void testAllArgsConstructor() {
        Event event = new Event(
                2L, "PLAGA", Instant.now(), "Insectos", 5, true
        );

        assertEquals("PLAGA", event.getType());
        assertTrue(event.isActive());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();

        Event event = Event.builder()
                .id(3L)
                .type("GUERRA")
                .startedAt(now)
                .description("Conflicto total")
                .severity(9)
                .active(true)
                .build();

        assertEquals("GUERRA", event.getType());
        assertEquals(9, event.getSeverity());
    }

    @Test
    void testToString() {
        Event event = new Event();
        assertNotNull(event.toString());
    }
}
