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

    // ----------------- CREATE -----------------

    @Test
    void create_happyPath() {
        Faction f = Faction.builder().aggression(0.5).expansionism(0.5).size(5).build();
        when(factionDAO.save(any())).thenAnswer(i -> i.getArgument(0));
        Faction res = factionService.create(f);
        assertNotNull(res);
    }

    @Test
    void create_invalidAggression_throws() {
        Faction f = Faction.builder().aggression(1.1).expansionism(0.5).size(1).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(f));
        assertTrue(ex.getMessage().contains("Aggression"));
    }

    @Test
    void create_invalidExpansionism_throws() {
        Faction f = Faction.builder().aggression(0.5).expansionism(-0.1).size(1).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(f));
        assertTrue(ex.getMessage().contains("Expansionism"));
    }

    @Test
    void create_invalidSize_throws() {
        Faction f = Faction.builder().aggression(0.5).expansionism(0.5).size(-1).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.create(f));
        assertTrue(ex.getMessage().contains("Size"));
    }

    @Test
    void create_withId_regionExists() {
        Region r = new Region();
        Faction f = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        when(regionDAO.findById(1L)).thenReturn(r);
        when(factionDAO.save(any())).thenAnswer(i -> i.getArgument(0));
        Faction res = factionService.create(f);
        assertNotNull(res);
    }

    @Test
    void create_withId_regionNotFound_throws() {
        Faction f = Faction.builder().id(99L).aggression(0.5).expansionism(0.5).size(5).build();
        when(regionDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.create(f));
        assertTrue(ex.getMessage().contains("Region not found"));
    }

    // ----------------- UPDATE -----------------

    @Test
    void update_happyPath() {
        Faction existing = Faction.builder().id(1L).aggression(0.2).expansionism(0.3).size(5).build();
        Faction update = Faction.builder().aggression(0.8).expansionism(0.9).size(10).build();
        when(factionDAO.findById(1L)).thenReturn(existing);
        when(factionDAO.save(any())).thenAnswer(i -> i.getArgument(0));
        Faction res = factionService.update(1L, update);
        assertEquals(0.8, res.getAggression());
        assertEquals(0.9, res.getExpansionism());
        assertEquals(10, res.getSize());
    }

    @Test
    void update_invalidAggression_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(1.2).expansionism(0.5).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Aggression"));
    }

    @Test
    void update_invalidExpansionism_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(0.5).expansionism(-0.1).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Expansionism"));
    }

    @Test
    void update_invalidSize_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().aggression(0.5).expansionism(0.5).size(-1).build();
        when(factionDAO.findById(1L)).thenReturn(existing);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Size"));
    }

    @Test
    void update_withId_regionNotFound_throws() {
        Faction existing = Faction.builder().id(1L).aggression(0.5).expansionism(0.5).size(5).build();
        Faction update = Faction.builder().id(99L).aggression(0.5).expansionism(0.5).size(5).build();
        when(factionDAO.findById(1L)).thenReturn(existing);
        when(regionDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.update(1L, update));
        assertTrue(ex.getMessage().contains("Region not found"));
    }

    // ----------------- FIND -----------------

    @Test
    void findById_happyPath() {
        Faction f = Faction.builder().id(1L).build();
        when(factionDAO.findById(1L)).thenReturn(f);
        Faction res = factionService.findById(1L);
        assertEquals(1L, res.getId());
    }

    @Test
    void findById_notFound_throws() {
        when(factionDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.findById(99L));
        assertTrue(ex.getMessage().contains("Faction not found"));
    }

    @Test
    void findAll_happyPath() {
        Faction f1 = Faction.builder().id(1L).build();
        Faction f2 = Faction.builder().id(2L).build();
        when(factionDAO.findAll()).thenReturn(List.of(f1, f2));
        List<Faction> res = factionService.findAll();
        assertEquals(2, res.size());
    }

    @Test
    void findAll_emptyList() {
        when(factionDAO.findAll()).thenReturn(List.of());
        List<Faction> res = factionService.findAll();
        assertTrue(res.isEmpty());
    }

    // ----------------- DELETE -----------------

    @Test
    void delete_happyPath() {
        Faction existing = Faction.builder().id(2L).build();
        when(factionDAO.findById(2L)).thenReturn(existing);
        doNothing().when(factionDAO).delete(existing);
        factionService.delete(2L);
        verify(factionDAO, times(1)).delete(existing);
    }
}
