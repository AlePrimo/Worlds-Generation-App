package com.primo.worldgen_backend.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primo.worldgen_backend.dto.world.WorldRequestDTO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.WorldMapper;
import com.primo.worldgen_backend.service.WorldService;
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


@WebMvcTest(WorldController.class)
public class WorldControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private WorldService worldService;


    @MockitoBean
    private WorldMapper worldMapper;


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createWorld_returnsCreated() throws Exception {
        WorldRequestDTO req = WorldRequestDTO.builder().name("Test").build();
        World entity = World.builder().id(1L).name("Test").createdAt(Instant.now()).ticks(0).build();
        WorldResponseDTO resp = WorldResponseDTO.builder().id(1L).name("Test").createdAt(entity.getCreatedAt()).ticks(0).build();


        Mockito.when(worldMapper.toEntity(any(WorldRequestDTO.class))).thenReturn(entity);
        Mockito.when(worldService.create(any(World.class))).thenReturn(entity);
        Mockito.when(worldMapper.toDTO(any(World.class))).thenReturn(resp);


        mockMvc.perform(post("/api/worlds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    void getAll_returnsOk() throws Exception {
        World entity = World.builder().id(1L).name("Test").createdAt(Instant.now()).ticks(0).build();
        WorldResponseDTO resp = WorldResponseDTO.builder().id(1L).name("Test").createdAt(entity.getCreatedAt()).ticks(0).build();


        Mockito.when(worldService.findAll()).thenReturn(List.of(entity));
        Mockito.when(worldMapper.toDTO(entity)).thenReturn(resp);


        mockMvc.perform(get("/api/worlds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}