package com.primo.worldgen_backend.entities;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Event event = new Event();
        Region region = new Region();
        Instant now = Instant.now();

        event.setId(1L);
        event.setType("SEQUIA");
        event.setStartedAt(now);
        event.setDescription("Sequía prolongada");
        event.setSeverity(7);
        event.setActive(false);
        event.setRegion(region);

        assertEquals(1L, event.getId());
        assertEquals("SEQUIA", event.getType());
        assertEquals(now, event.getStartedAt());
        assertEquals("Sequía prolongada", event.getDescription());
        assertEquals(7, event.getSeverity());
        assertFalse(event.isActive());
        assertEquals(region, event.getRegion());
    }

    @Test
    void testAllArgsConstructor() {
        Region region = new Region();
        Instant now = Instant.now();

        Event event = new Event(
                2L,
                "PLAGA",
                now,
                "Insectos",
                5,
                true,
                region
        );

        assertEquals(2L, event.getId());
        assertEquals("PLAGA", event.getType());
        assertEquals(now, event.getStartedAt());
        assertEquals("Insectos", event.getDescription());
        assertEquals(5, event.getSeverity());
        assertTrue(event.isActive());
        assertEquals(region, event.getRegion());
    }

    @Test
    void testBuilder() {
        Region region = new Region();
        Instant now = Instant.now();

        Event event = Event.builder()
                .id(3L)
                .type("GUERRA")
                .startedAt(now)
                .description("Conflicto total")
                .severity(9)
                .active(true)
                .region(region)
                .build();

        assertEquals(3L, event.getId());
        assertEquals("GUERRA", event.getType());
        assertEquals("Conflicto total", event.getDescription());
        assertEquals(9, event.getSeverity());
        assertTrue(event.isActive());
        assertEquals(region, event.getRegion());
    }

    @Test
    void testToStringNotNull() {
        Event event = new Event();
        assertNotNull(event.toString());
    }
}
