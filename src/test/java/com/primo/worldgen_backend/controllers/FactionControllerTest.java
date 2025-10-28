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
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FactionController.class)
public class FactionControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private FactionService factionService;


    @MockBean
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
    void getAll_returnsOk() throws Exception {
        Faction entity = Faction.builder().id(1L).name("F1").build();
        FactionResponseDTO resp = FactionResponseDTO.builder().id(1L).name("F1").build();


        Mockito.when(factionService.findAll()).thenReturn(List.of(entity));
        Mockito.when(factionMapper.toDTO(entity)).thenReturn(resp);


        mockMvc.perform(get("/api/factions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}