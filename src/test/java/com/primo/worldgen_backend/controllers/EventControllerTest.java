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
import org.springframework.context.annotation.Import;
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
@Import(EventMapper.class) // Usamos mapper real para convertir entidades a DTO
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService; // solo mockeamos el servicio

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
        when(eventService.create(any(Event.class))).thenReturn(event);

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
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("SEQUIA"))
                .andExpect(jsonPath("$.severity").value(7));
    }

    @Test
    void getById_returnsOk() throws Exception {
        when(eventService.findById(1L)).thenReturn(event);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("SEQUIA"));
    }

    @Test
    void list_returnsOk() throws Exception {
        when(eventService.findAll()).thenReturn(List.of(event));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].type").value("SEQUIA"));
    }

    @Test
    void update_returnsOk() throws Exception {
        // Creamos una nueva entidad que simule la actualización
        Event updatedEvent = Event.builder()
                .id(1L)
                .type("SEQUIA")
                .startedAt(requestDTO.getStartedAt())
                .description("Sequía actualizada") // <--- reflejamos el cambio
                .severity(8)
                .active(true)
                .build();

        when(eventService.update(Mockito.eq(1L), any(Event.class))).thenReturn(updatedEvent);

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
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("SEQUIA"))
                .andExpect(jsonPath("$.description").value("Sequía actualizada"))
                .andExpect(jsonPath("$.severity").value(8));
    }


    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());
    }
}
