package com.primo.worldgen_backend.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Region region = new Region();

        region.setId(1L);
        region.setName("Bosque Norte");
        region.setLat(-10.5);
        region.setLon(20.7);
        region.setPopulation(500);
        region.setWater(75.5);
        region.setFood(60.2);
        region.setMinerals(120.7);
        region.setFactions(List.of());
        region.setEvents(List.of());
        region.setAlive(false);

        assertEquals(1L, region.getId());
        assertEquals("Bosque Norte", region.getName());
        assertEquals(-10.5, region.getLat());
        assertEquals(20.7, region.getLon());
        assertEquals(500, region.getPopulation());
        assertEquals(75.5, region.getWater());
        assertEquals(60.2, region.getFood());
        assertEquals(120.7, region.getMinerals());
        assertNotNull(region.getFactions());
        assertNotNull(region.getEvents());
        assertFalse(region.isAlive());
    }

    @Test
    void testAllArgsConstructor() {
        Region region = new Region(
                2L, "Desierto Sur", 1.2, 2.3, 200, 10.5, 5.5, 30.0,
                List.of(), List.of(), true
        );

        assertEquals("Desierto Sur", region.getName());
        assertTrue(region.isAlive());
    }

    @Test
    void testBuilder() {
        Region region = Region.builder()
                .id(3L)
                .name("Valle Verde")
                .lat(11.1)
                .lon(22.2)
                .population(300)
                .water(40.0)
                .food(70.0)
                .minerals(99.9)
                .factions(List.of())
                .events(List.of())
                .alive(true)
                .build();

        assertEquals("Valle Verde", region.getName());
        assertEquals(300, region.getPopulation());
        assertTrue(region.isAlive());
    }

    @Test
    void testToString() {
        Region region = new Region();
        assertNotNull(region.toString());
    }
}
