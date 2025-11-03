package com.primo.worldgen_backend.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {


    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Region region = new Region();
        World world = new World();
        world.setId(1L);
        world.setName("Zyrion");

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
        region.setWorld(world);

        assertEquals(1L, region.getId());
        assertEquals("Bosque Norte", region.getName());
        assertEquals(world, region.getWorld());
        assertFalse(region.isAlive());
    }

    @Test
    void testAllArgsConstructor() {
        World world = new World();
        world.setId(2L);

        Region region = new Region(
                2L, "Desierto Sur", 1.2, 2.3, 200, 10.5, 5.5, 30.0,
                List.of(), List.of(), world, true
        );

        assertEquals("Desierto Sur", region.getName());
        assertEquals(world, region.getWorld());
        assertTrue(region.isAlive());
    }

    @Test
    void testBuilder() {
        World world = World.builder().id(3L).name("Gaia").build();

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
                .world(world)
                .alive(true)
                .build();

        assertEquals("Valle Verde", region.getName());
        assertEquals(world, region.getWorld());
        assertTrue(region.isAlive());
    }

    @Test
    void testToString() {
        Region region = new Region();
        assertNotNull(region.toString());
    }
}
