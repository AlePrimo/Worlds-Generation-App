package com.primo.worldgen_backend.controllers;

import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private EventService eventService;
    private EventController controller;

    @BeforeEach
    void setup() {
        // Creamos un EventService "dummy" que hace lo mínimo
        eventService = new EventService() {
            @Override
            public Event create(Event event) {
                event.setId(1L);
                return event;
            }

            @Override
            public Event findById(Long id) {
                return Event.builder().id(id).type("TIPO_TEST").build();
            }

            @Override
            public List<Event> findAll() {
                return List.of(Event.builder().id(2L).type("LISTADO_TEST").build());
            }

            @Override
            public Event update(Long id, Event event) {
                event.setId(id);
                return event;
            }

            @Override
            public void delete(Long id) {
                // nada
            }
        };

        controller = new EventController(eventService, new com.primo.worldgen_backend.mappers.EventMapper() {});
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createEvent_returnsCreated() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"SEQUIA\",\"description\":\"desc\",\"severity\":5,\"active\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("SEQUIA"));
    }

    @Test
    void getById_returnsOk() throws Exception {
        mockMvc.perform(get("/api/events/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.type").value("TIPO_TEST"));
    }

    @Test
    void list_returnsOk() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].type").value("LISTADO_TEST"));
    }

    @Test
    void update_returnsOk() throws Exception {
        mockMvc.perform(put("/api/events/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"PLAGA\",\"description\":\"desc2\",\"severity\":7,\"active\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.type").value("PLAGA"));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/events/5"))
                .andExpect(status().isNoContent());
    }
}
