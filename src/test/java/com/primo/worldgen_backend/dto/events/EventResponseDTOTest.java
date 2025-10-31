package com.primo.worldgen_backend.dto.events;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class EventResponseDTOTest {

    @Test
    void fullCoverage_allConstructorsAndBuilder() {
        Instant now = Instant.now();

        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(10L);
        dto.setType("ERUPCION");
        dto.setStartedAt(now);
        dto.setDescription("Volcán activo");
        dto.setSeverity(10);
        dto.setActive(false);

        assertEquals(10L, dto.getId());
        assertEquals("ERUPCION", dto.getType());
        assertEquals(now, dto.getStartedAt());
        assertEquals("Volcán activo", dto.getDescription());
        assertEquals(10, dto.getSeverity());
        assertFalse(dto.isActive());

        EventResponseDTO built = EventResponseDTO.builder()
                .id(1L)
                .type("TSUNAMI")
                .startedAt(now)
                .description("Olas gigantes")
                .severity(9)
                .active(true)
                .build();

        assertEquals("TSUNAMI", built.getType());
        assertTrue(built.isActive());

        EventResponseDTO allArgs = new EventResponseDTO(2L, "PLAGA", now, "Insectos", 4, true);
        assertEquals("PLAGA", allArgs.getType());
    }
}

