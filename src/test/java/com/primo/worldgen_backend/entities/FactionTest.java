package com.primo.worldgen_backend.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactionTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Faction faction = new Faction();

        faction.setId(1L);
        faction.setName("Clan Norte");
        faction.setAggression(0.7);
        faction.setExpansionism(0.5);
        faction.setSize(120);

        assertEquals(1L, faction.getId());
        assertEquals("Clan Norte", faction.getName());
        assertEquals(0.7, faction.getAggression());
        assertEquals(0.5, faction.getExpansionism());
        assertEquals(120, faction.getSize());
    }

    @Test
    void testAllArgsConstructor() {
        Faction faction = new Faction(2L, "Clan Sur", 0.2, 0.8, 300);

        assertEquals("Clan Sur", faction.getName());
        assertEquals(300, faction.getSize());
    }

    @Test
    void testBuilder() {
        Faction faction = Faction.builder()
                .id(3L)
                .name("Clan Este")
                .aggression(0.3)
                .expansionism(0.9)
                .size(220)
                .build();

        assertEquals("Clan Este", faction.getName());
        assertEquals(0.9, faction.getExpansionism());
    }

    @Test
    void testToString() {
        Faction faction = new Faction();
        assertNotNull(faction.toString());
    }
}
