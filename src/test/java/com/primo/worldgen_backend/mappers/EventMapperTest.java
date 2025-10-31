package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {

    private final EventMapper mapper = new EventMapper();

    @Test
    void toEntity_withActiveTrue_setsCorrectly() {
        Instant now = Instant.now();
        EventRequestDTO dto = EventRequestDTO.builder()
                .type("Flood")
                .startedAt(now)
                .description("Big flood")
                .severity(4)
                .active(true)
                .build();

        Event entity = mapper.toEntity(dto);

        assertEquals("Flood", entity.getType());
        assertEquals(now, entity.getStartedAt());
        assertTrue(entity.isActive());
    }

    @Test
    void toEntity_withActiveNull_defaultsToTrue() {
        EventRequestDTO dto = EventRequestDTO.builder()
                .type("Drought")
                .startedAt(Instant.now())
                .description("Severe drought")
                .severity(3)
                .active(null)
                .build();

        Event entity = mapper.toEntity(dto);

        assertTrue(entity.isActive());
    }

    @Test
    void toDTO_ok() {
        Instant now = Instant.now();
        Event entity = Event.builder()
                .id(10L)
                .type("Storm")
                .startedAt(now)
                .description("Thunderstorm")
                .severity(6)
                .active(false)
                .build();

        EventResponseDTO dto = mapper.toDTO(entity);

        assertEquals(10L, dto.getId());
        assertEquals("Storm", dto.getType());
        assertEquals(now, dto.getStartedAt());
        assertFalse(dto.isActive());
        assertEquals(6, dto.getSeverity());
    }
}
