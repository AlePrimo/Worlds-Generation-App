package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.entities.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {


private final EventMapper eventMapper = new EventMapper();

    @Test
    void toEntity_ok() {
        Instant now = Instant.now();
        EventRequestDTO dto = EventRequestDTO.builder()
                .type("SEQUIA")
                .startedAt(now)
                .description("desc")
                .severity(5)
                .active(true)
                .build();

        Event e = eventMapper.toEntity(dto);

        assertEquals("SEQUIA", e.getType());
        assertEquals(now, e.getStartedAt());
        assertTrue(e.isActive());
    }

    @Test
    void toDTO_ok() {
        Event entity = Event.builder()
                .id(1L)
                .type("VULCANISMO")
                .startedAt(Instant.now())
                .description("desc")
                .severity(8)
                .active(false)
                .build();

        var dto = eventMapper.toDTO(entity);
        assertEquals(1L, dto.getId());
        assertEquals("VULCANISMO", dto.getType());
    }
}
