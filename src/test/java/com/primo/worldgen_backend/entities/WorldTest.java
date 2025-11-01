package com.primo.worldgen_backend.entities;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        World world = new World();
        Instant now = Instant.now();

        world.setId(1L);
        world.setName("Zyrion");
        world.setCreatedAt(now);
        world.setRegions(List.of());
        world.setTicks(5);

        assertEquals(1L, world.getId());
        assertEquals("Zyrion", world.getName());
        assertEquals(now, world.getCreatedAt());
        assertNotNull(world.getRegions());
        assertEquals(5, world.getTicks());
    }

    @Test
    void testAllArgsConstructor() {
        World world = new World(
                2L,
                "Aether",
                Instant.now(),
                List.of(),
                10
        );

        assertEquals("Aether", world.getName());
        assertEquals(10, world.getTicks());
    }

    @Test
    void testBuilder() {
        Instant now = Instant.now();

        World world = World.builder()
                .id(3L)
                .name("Nebula")
                .createdAt(now)
                .regions(List.of())
                .ticks(0)
                .build();

        assertEquals("Nebula", world.getName());
        assertEquals(0, world.getTicks());
    }

    @Test
    void testToString() {
        World world = new World();
        assertNotNull(world.toString());
    }
}
