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


import static org.mockito.ArgumentMatchers.any;
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
    void getAll_returnsOk() throws Exception {
        Region entity = Region.builder().id(1L).name("R1").build();
        RegionResponseDTO resp = RegionResponseDTO.builder().id(1L).name("R1").build();


        Mockito.when(regionService.findAll()).thenReturn(List.of(entity));
        Mockito.when(regionMapper.toDTO(entity)).thenReturn(resp);


        mockMvc.perform(get("/api/regions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}