package com.primo.worldgen_backend.dto.events;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class EventRequestDTOTest {

    @Test
    void builder_ok() {
        Instant now = Instant.now();
        EventRequestDTO dto = EventRequestDTO.builder()
                .type("SEQUIA")
                .startedAt(now)
                .description("d")
                .severity(3)
                .active(true)
                .build();

        assertEquals("SEQUIA", dto.getType());
        assertEquals(now, dto.getStartedAt());
    }
}
