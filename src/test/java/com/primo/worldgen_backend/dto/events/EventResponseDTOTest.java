package com.primo.worldgen_backend.dto.events;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventResponseDTOTest {

    @Test
    void builder_ok() {
        EventResponseDTO dto = EventResponseDTO.builder()
                .id(1L)
                .type("PLAGA")
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("PLAGA", dto.getType());
    }
}
