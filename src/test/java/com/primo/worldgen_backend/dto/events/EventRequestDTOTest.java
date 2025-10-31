package com.primo.worldgen_backend.dto.events;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class EventRequestDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        Instant now = Instant.now();

        EventRequestDTO dto = new EventRequestDTO();
        dto.setType("SEQUIA");
        dto.setStartedAt(now);
        dto.setDescription("Periodo de sequía prolongada");
        dto.setSeverity(5);
        dto.setActive(true);

        assertEquals("SEQUIA", dto.getType());
        assertEquals(now, dto.getStartedAt());
        assertEquals("Periodo de sequía prolongada", dto.getDescription());
        assertEquals(5, dto.getSeverity());
        assertTrue(dto.getActive());

        EventRequestDTO built = EventRequestDTO.builder()
                .type("PLAGA")
                .startedAt(now)
                .description("Insectos")
                .severity(7)
                .active(false)
                .build();

        assertEquals("PLAGA", built.getType());
        assertFalse(built.getActive());

        EventRequestDTO allArgs = new EventRequestDTO("GUERRA", now, "Conflicto entre facciones", 9, true);
        assertEquals("GUERRA", allArgs.getType());
        assertTrue(allArgs.getActive());
    }
}
