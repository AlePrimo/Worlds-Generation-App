package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.impl.FactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FactionServiceImplTest {

    @Mock
    private FactionDAO factionDAO;

    @Mock
    private RegionDAO regionDAO;

    @InjectMocks
    private FactionServiceImpl factionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------------- CREATE ----------------
    @Test
    void create_validFaction_saves() {
        Faction faction = Faction.builder().aggression(0.5).expansionism(0.5).size(5).build();
        when(factionDAO.save(any(Faction.class))).thenAnswer(i -> i.getArgument(0));

        Faction saved = factionService.create(faction);

        assertNotNull(saved);
        verify(factionDAO, times(1)).save(faction);
    }

    @Test
    void create_factionAggressionTooLow_throws() {
        Faction faction = Faction.builder().aggression(-0.1).expansionism(0.5).size(5).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(faction));
        assertTrue(ex.getMessage().contains("Aggression must be between 0.0 and 1.0"));
    }

    @Test
    void create_factionAggressionTooHigh_throws() {
        Faction faction = Faction.builder().aggression(1.1).expansionism(0.5).size(5).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(faction));
        assertTrue(ex.getMessage().contains("Aggression must be between 0.0 and 1.0"));
    }

    @Test
    void create_factionExpansionismTooLow_throws() {
        Faction faction = Faction.builder().aggression(0.5).expansionism(-0.1).size(5).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(faction));
        assertTrue(ex.getMessage().contains("Expansionism must be between 0.0 and 1.0"));
    }

    @Test
    void create_factionExpansionismTooHigh_throws() {
        Faction faction = Faction.builder().aggression(0.5).expansionism(1.1).size(5).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(faction));
        assertTrue(ex.getMessage().contains("Expansionism must be between 0.0 and 1.0"));
    }

    @Test
    void create_factionSizeInvalid_throws() {
        Faction faction = Faction.builder().aggression(0.5).expansionism(0.5).size(-1).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(faction));
        assertTrue(ex.getMessage().contains("Size must be >= 0"));
    }

    @Test
    void create_factionWithId_regionExists() {
        Faction faction = Faction.builder().id(10L).aggression(0.5).expansionism(0.5).size(5).build();
        Region region = Region.builder().id(10L).build();

        when(regionDAO.findById(10L)).thenReturn(region);
        when(factionDAO.save(any(Faction.class))).thenAnswer(i -> i.getArgument(0));

        Faction saved = factionService.create(faction);

        assertNotNull(saved);
        verify(regionDAO, times(1)).findById(10L);
    }

    @Test
    void create_factionWithId_regionDoesNotExist_throws() {
        Faction faction = Faction.builder().id(20L).aggression(0.5).expansionism(0.5).size(5).build();
        when(regionDAO.findById(20L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.create(faction));
        assertTrue(ex.getMessage().contains("Region not found"));
        verify(regionDAO, times(1)).findById(20L);
    }

    // ---------------- UPDATE ----------------
    @Test
    void update_existing_updatesFields() {
        Faction existing = Faction.builder().id(1L).name("Old").aggression(0.2).expansionism(0.3).size(5).build();
        Faction update = Faction.builder().name("New").aggression(0.9).expansionism(0.8).size(10).build();

        when(factionDAO.findById(1L)).thenReturn(existing);
        when(factionDAO.save(any(Faction.class))).thenAnswer(i -> i.getArgument(0));

        Faction res = factionService.update(1L, update);

        assertEquals("New", res.getName());
        assertEquals(0.9, res.getAggression());
        assertEquals(0.8, res.getExpansionism());
        assertEquals(10, res.getSize());
    }

    @Test
    void update_factionAggressionTooLow_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(-0.1).expansionism(0.5).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Aggression must be between 0.0 and 1.0"));
    }

    @Test
    void update_factionAggressionTooHigh_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(1.1).expansionism(0.5).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Aggression must be between 0.0 and 1.0"));
    }

    @Test
    void update_factionExpansionismTooLow_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(0.5).expansionism(-0.1).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Expansionism must be between 0.0 and 1.0"));
    }

    @Test
    void update_factionExpansionismTooHigh_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(0.5).expansionism(1.1).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Expansionism must be between 0.0 and 1.0"));
    }

    @Test
    void update_factionSizeInvalid_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(0.5).expansionism(0.5).size(-1).build();
        when(factionDAO.findById(1L)).thenReturn(existing);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Size must be >= 0"));
    }

    @Test
    void update_factionWithId_regionExists() {
        Faction existing = Faction.builder().id(1L).name("Old").aggression(0.2).expansionism(0.3).size(5).build();
        Faction update = Faction.builder().id(100L).name("New").aggression(0.5).expansionism(0.5).size(5).build();
        Region region = Region.builder().id(100L).build();

        when(factionDAO.findById(1L)).thenReturn(existing);
        when(regionDAO.findById(100L)).thenReturn(region);
        when(factionDAO.save(any(Faction.class))).thenAnswer(i -> i.getArgument(0));

        Faction updated = factionService.update(1L, update);

        assertEquals("New", updated.getName());
        verify(regionDAO, times(1)).findById(100L);
    }

    @Test
    void update_factionWithId_regionDoesNotExist_throws() {
        Faction existing = Faction.builder().id(1L).name("Old").aggression(0.2).expansionism(0.3).size(5).build();
        Faction update = Faction.builder().id(100L).name("New").aggression(0.5).expansionism(0.5).size(5).build();

        when(factionDAO.findById(1L)).thenReturn(existing);
        when(regionDAO.findById(100L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Region not found"));
        verify(regionDAO, times(1)).findById(100L);
    }

    // ---------------- FIND & DELETE ----------------
    @Test
    void findById_existing_returnsFaction() {
        Faction faction = Faction.builder().id(1L).build();
        when(factionDAO.findById(1L)).thenReturn(faction);

        Faction found = factionService.findById(1L);
        assertEquals(faction, found);
    }

    @Test
    void findById_notFound_throws() {
        when(factionDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.findById(99L));
        assertTrue(ex.getMessage().contains("Faction not found"));
    }

    @Test
    void findAll_returnsList() {
        Faction faction = Faction.builder().id(1L).build();
        when(factionDAO.findAll()).thenReturn(List.of(faction));

        List<Faction> list = factionService.findAll();
        assertEquals(1, list.size());
        assertEquals(faction, list.get(0));
    }

    @Test
    void delete_existing_deletes() {
        Faction existing = Faction.builder().id(2L).build();
        when(factionDAO.findById(2L)).thenReturn(existing);
        doNothing().when(factionDAO).delete(existing);

        factionService.delete(2L);
        verify(factionDAO, times(1)).delete(existing);
    }
}