package com.primo.worldgen_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.mappers.FactionMapper;
import com.primo.worldgen_backend.service.FactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FactionController.class)
public class FactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FactionService factionService;

    @MockitoBean
    private FactionMapper factionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createFaction_returnsCreated() throws Exception {
        FactionRequestDTO req = FactionRequestDTO.builder().name("F1").aggression(0.5).expansionism(0.2).size(10).build();
        Faction entity = Faction.builder().id(1L).name("F1").build();
        FactionResponseDTO resp = FactionResponseDTO.builder().id(1L).name("F1").build();

        Mockito.when(factionMapper.toEntity(any(FactionRequestDTO.class))).thenReturn(entity);
        Mockito.when(factionService.create(any(Faction.class))).thenReturn(entity);
        Mockito.when(factionMapper.toDTO(any(Faction.class))).thenReturn(resp);

        mockMvc.perform(post("/api/factions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById_returnsOk() throws Exception {
        Faction entity = Faction.builder().id(2L).name("F2").build();
        FactionResponseDTO resp = FactionResponseDTO.builder().id(2L).name("F2").build();

        Mockito.when(factionService.findById(2L)).thenReturn(entity);
        Mockito.when(factionMapper.toDTO(entity)).thenReturn(resp);

        mockMvc.perform(get("/api/factions/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void list_returnsOk() throws Exception {
        Faction e = Faction.builder().id(3L).name("F3").build();
        FactionResponseDTO r = FactionResponseDTO.builder().id(3L).name("F3").build();

        Mockito.when(factionService.findAll()).thenReturn(List.of(e));
        Mockito.when(factionMapper.toDTO(e)).thenReturn(r);

        mockMvc.perform(get("/api/factions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void update_returnsOk() throws Exception {
        FactionRequestDTO req = FactionRequestDTO.builder().name("Fupdated").aggression(0.1).expansionism(0.2).size(5).build();
        Faction in = Faction.builder().name("Fupdated").build();
        Faction out = Faction.builder().id(4L).name("Fupdated").build();
        FactionResponseDTO resp = FactionResponseDTO.builder().id(4L).name("Fupdated").build();

        Mockito.when(factionMapper.toEntity(any(FactionRequestDTO.class))).thenReturn(in);
        Mockito.when(factionService.update(eq(4L), any(Faction.class))).thenReturn(out);
        Mockito.when(factionMapper.toDTO(out)).thenReturn(resp);

        mockMvc.perform(put("/api/factions/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        willDoNothing().given(factionService).delete(5L);
        mockMvc.perform(delete("/api/factions/5"))
                .andExpect(status().isNoContent());
    }
}
