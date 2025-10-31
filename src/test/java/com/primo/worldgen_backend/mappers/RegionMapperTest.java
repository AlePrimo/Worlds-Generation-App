package com.primo.worldgen_backend.mappers;

import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegionMapperTest {

    private final RegionMapper mapper = new RegionMapper();

    @Test
    void toEntity_ok() {
        RegionRequestDTO dto = RegionRequestDTO.builder()
                .name("Northlands")
                .lat(12.34)
                .lon(56.78)
                .population(500)
                .water(200)
                .food(300)
                .minerals(100)
                .alive(true)
                .build();

        Region entity = mapper.toEntity(dto);

        assertEquals("Northlands", entity.getName());
        assertEquals(12.34, entity.getLat());
        assertTrue(entity.isAlive());
    }

    @Test
    void toDTO_withFactionsAndEvents_ok() {
        Faction f1 = Faction.builder().id(1L).build();
        Faction f2 = Faction.builder().id(2L).build();
        Event e1 = Event.builder().id(10L).build();
        Event e2 = Event.builder().id(20L).build();

        Region region = Region.builder()
                .id(99L)
                .name("Midlands")
                .lat(1.0)
                .lon(2.0)
                .population(1000)
                .water(200)
                .food(300)
                .minerals(400)
                .alive(true)
                .factions(List.of(f1, f2))
                .events(List.of(e1, e2))
                .build();

        RegionResponseDTO dto = mapper.toDTO(region);

        assertEquals(99L, dto.getId());
        assertEquals(List.of(1L, 2L), dto.getFactionIds());
        assertEquals(List.of(10L, 20L), dto.getEventIds());
    }

    @Test
    void toDTO_withNullLists_returnsNulls() {
        Region region = Region.builder()
                .id(5L)
                .name("Empty")
                .alive(false)
                .build();

        RegionResponseDTO dto = mapper.toDTO(region);

        assertNull(dto.getFactionIds());
        assertNull(dto.getEventIds());
    }
}
