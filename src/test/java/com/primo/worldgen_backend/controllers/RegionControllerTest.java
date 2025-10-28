package com.primo.worldgen_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.mappers.RegionMapper;
import com.primo.worldgen_backend.service.RegionService;
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

@WebMvcTest(RegionController.class)
public class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegionService regionService;

    @MockitoBean
    private RegionMapper regionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRegion_returnsCreated() throws Exception {
        RegionRequestDTO req = RegionRequestDTO.builder().name("R1").lat(-10).lon(20).population(100).water(50).food(40).minerals(10).alive(true).build();
        Region entity = Region.builder().id(1L).name("R1").build();
        RegionResponseDTO resp = RegionResponseDTO.builder().id(1L).name("R1").build();

        Mockito.when(regionMapper.toEntity(any(RegionRequestDTO.class))).thenReturn(entity);
        Mockito.when(regionService.create(any(Region.class))).thenReturn(entity);
        Mockito.when(regionMapper.toDTO(any(Region.class))).thenReturn(resp);

        mockMvc.perform(post("/api/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getById_returnsOk() throws Exception {
        Region entity = Region.builder().id(2L).name("R2").build();
        RegionResponseDTO resp = RegionResponseDTO.builder().id(2L).name("R2").build();

        Mockito.when(regionService.findById(2L)).thenReturn(entity);
        Mockito.when(regionMapper.toDTO(entity)).thenReturn(resp);

        mockMvc.perform(get("/api/regions/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void getById_notFound_returns500() throws Exception {
        Mockito.when(regionService.findById(99L)).thenThrow(new RuntimeException("Region not found with id 99"));
        mockMvc.perform(get("/api/regions/99"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void list_returnsOk() throws Exception {
        Region e = Region.builder().id(3L).name("R3").build();
        RegionResponseDTO r = RegionResponseDTO.builder().id(3L).name("R3").build();

        Mockito.when(regionService.findAll()).thenReturn(List.of(e));
        Mockito.when(regionMapper.toDTO(e)).thenReturn(r);

        mockMvc.perform(get("/api/regions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void update_returnsOk() throws Exception {
        RegionRequestDTO req = RegionRequestDTO.builder().name("Rupdated").lat(1).lon(2).population(10).water(2).food(3).minerals(4).alive(false).build();
        Region in = Region.builder().name("Rupdated").build();
        Region out = Region.builder().id(4L).name("Rupdated").build();
        RegionResponseDTO resp = RegionResponseDTO.builder().id(4L).name("Rupdated").build();

        Mockito.when(regionMapper.toEntity(any(RegionRequestDTO.class))).thenReturn(in);
        Mockito.when(regionService.update(eq(4L), any(Region.class))).thenReturn(out);
        Mockito.when(regionMapper.toDTO(out)).thenReturn(resp);

        mockMvc.perform(put("/api/regions/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        willDoNothing().given(regionService).delete(5L);
        mockMvc.perform(delete("/api/regions/5"))
                .andExpect(status().isNoContent());
    }
}
