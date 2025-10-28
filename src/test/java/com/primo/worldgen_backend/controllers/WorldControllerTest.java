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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
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
    void createWorld_returnsCreatedAndBody() throws Exception {
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
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void getById_returnsOk() throws Exception {
        World entity = World.builder().id(2L).name("W2").createdAt(Instant.now()).ticks(5).build();
        WorldResponseDTO resp = WorldResponseDTO.builder().id(2L).name("W2").createdAt(entity.getCreatedAt()).ticks(5).build();

        Mockito.when(worldService.findById(2L)).thenReturn(entity);
        Mockito.when(worldMapper.toDTO(entity)).thenReturn(resp);

        mockMvc.perform(get("/api/worlds/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.ticks").value(5));
    }

    @Test
    void getById_notFound_returns500() throws Exception {
        Mockito.when(worldService.findById(99L)).thenThrow(new RuntimeException("World not found with id 99"));
        mockMvc.perform(get("/api/worlds/99"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void list_returnsOk() throws Exception {
        World e = World.builder().id(3L).name("W3").createdAt(Instant.now()).ticks(1).build();
        WorldResponseDTO r = WorldResponseDTO.builder().id(3L).name("W3").createdAt(e.getCreatedAt()).ticks(1).build();

        Mockito.when(worldService.findAll()).thenReturn(List.of(e));
        Mockito.when(worldMapper.toDTO(e)).thenReturn(r);

        mockMvc.perform(get("/api/worlds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void update_returnsOk() throws Exception {
        WorldRequestDTO req = WorldRequestDTO.builder().name("Updated").build();
        World in = World.builder().name("Updated").build();
        World out = World.builder().id(4L).name("Updated").createdAt(Instant.now()).ticks(2).build();
        WorldResponseDTO resp = WorldResponseDTO.builder().id(4L).name("Updated").createdAt(out.getCreatedAt()).ticks(2).build();

        Mockito.when(worldMapper.toEntity(any(WorldRequestDTO.class))).thenReturn(in);
        Mockito.when(worldService.update(eq(4L), any(World.class))).thenReturn(out);
        Mockito.when(worldMapper.toDTO(out)).thenReturn(resp);

        mockMvc.perform(put("/api/worlds/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        willDoNothing().given(worldService).delete(5L);
        mockMvc.perform(delete("/api/worlds/5"))
                .andExpect(status().isNoContent());
    }
}
