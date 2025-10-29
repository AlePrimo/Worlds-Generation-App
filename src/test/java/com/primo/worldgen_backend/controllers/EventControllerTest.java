package com.primo.worldgen_backend.controllers;

import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.mappers.EventMapper;
import com.primo.worldgen_backend.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private EventMapper eventMapper;

    private Event event;
    private EventRequestDTO requestDTO;
    private EventResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();

        requestDTO = EventRequestDTO.builder()
                .type("SEQUIA")
                .startedAt(now)
                .description("Sequía severa")
                .severity(7)
                .active(true)
                .build();

        event = Event.builder()
                .id(1L)
                .type("SEQUIA")
                .startedAt(now)
                .description("Sequía severa")
                .severity(7)
                .active(true)
                .build();

        responseDTO = EventResponseDTO.builder()
                .id(1L)
                .type("SEQUIA")
                .startedAt(now)
                .description("Sequía severa")
                .severity(7)
                .active(true)
                .build();
    }

    @Test
    void create_returnsCreated() throws Exception {
        when(eventMapper.toEntity(any(EventRequestDTO.class))).thenReturn(event);
        when(eventService.create(any(Event.class))).thenReturn(event);
        when(eventMapper.toDTO(any(Event.class))).thenReturn(responseDTO);

        String json = """
                {
                    "type": "SEQUIA",
                    "startedAt": "%s",
                    "description": "Sequía severa",
                    "severity": 7,
                    "active": true
                }
                """.formatted(requestDTO.getStartedAt().toString());

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("SEQUIA"))
                .andExpect(jsonPath("$.severity").value(7));
    }

    @Test
    void getById_returnsOk() throws Exception {
        when(eventService.findById(1L)).thenReturn(event);
        when(eventMapper.toDTO(event)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("SEQUIA"));
    }

    @Test
    void list_returnsOk() throws Exception {
        when(eventService.findAll()).thenReturn(List.of(event));
        when(eventMapper.toDTO(event)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("SEQUIA"));
    }

    @Test
    void update_returnsOk() throws Exception {
        when(eventMapper.toEntity(any(EventRequestDTO.class))).thenReturn(event);
        when(eventService.update(Mockito.eq(1L), any(Event.class))).thenReturn(event);
        when(eventMapper.toDTO(any(Event.class))).thenReturn(responseDTO);

        String json = """
                {
                    "type": "SEQUIA",
                    "startedAt": "%s",
                    "description": "Sequía actualizada",
                    "severity": 8,
                    "active": true
                }
                """.formatted(requestDTO.getStartedAt().toString());

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("SEQUIA"));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());
    }
}
