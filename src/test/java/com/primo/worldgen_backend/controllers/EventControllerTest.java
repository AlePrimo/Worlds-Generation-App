package com.primo.worldgen_backend.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.mappers.EventMapper;
import com.primo.worldgen_backend.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.Instant;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EventController.class)
public class EventControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private EventService eventService;


    @MockitoBean
    private EventMapper eventMapper;


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createEvent_returnsCreated() throws Exception {
        EventRequestDTO req = EventRequestDTO.builder().type("SEQUIA").startedAt(Instant.now()).description("desc").severity(5).active(true).build();
        Event entity = Event.builder().id(1L).type("SEQUIA").build();
        EventResponseDTO resp = EventResponseDTO.builder().id(1L).type("SEQUIA").build();


        Mockito.when(eventMapper.toEntity(any(EventRequestDTO.class))).thenReturn(entity);
        Mockito.when(eventService.create(any(Event.class))).thenReturn(entity);
        Mockito.when(eventMapper.toDTO(any(Event.class))).thenReturn(resp);


        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    void getAll_returnsOk() throws Exception {
        Event entity = Event.builder().id(1L).type("SEQUIA").build();
        EventResponseDTO resp = EventResponseDTO.builder().id(1L).type("SEQUIA").build();


        Mockito.when(eventService.findAll()).thenReturn(List.of(entity));
        Mockito.when(eventMapper.toDTO(entity)).thenReturn(resp);


        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}