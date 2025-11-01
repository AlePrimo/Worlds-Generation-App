package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.dao.impl.FactionDAOImpl;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.repository.FactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FactionDAOImplTest {

    @Mock
    private FactionRepository factionRepository;

    @InjectMocks
    private FactionDAOImpl factionDAO;

    public FactionDAOImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_delegatesToRepository() {
        Faction faction = new Faction();
        when(factionRepository.save(faction)).thenReturn(faction);

        Faction result = factionDAO.save(faction);

        assertEquals(faction, result);
        verify(factionRepository, times(1)).save(faction);
    }

    @Test
    void findAll_delegatesToRepository() {
        List<Faction> factions = List.of(new Faction(), new Faction());
        when(factionRepository.findAll()).thenReturn(factions);

        List<Faction> result = factionDAO.findAll();

        assertEquals(factions, result);
        verify(factionRepository, times(1)).findAll();
    }

    @Test
    void findById_found_returnsEntity() {
        Faction faction = new Faction();
        when(factionRepository.findById(1L)).thenReturn(Optional.of(faction));

        Faction result = factionDAO.findById(1L);

        assertEquals(faction, result);
        verify(factionRepository, times(1)).findById(1L);
    }

    @Test
    void findById_notFound_returnsNull() {
        when(factionRepository.findById(99L)).thenReturn(Optional.empty());

        Faction result = factionDAO.findById(99L);

        assertNull(result);
        verify(factionRepository, times(1)).findById(99L);
    }

    @Test
    void delete_delegatesToRepository() {
        Faction faction = new Faction();

        factionDAO.delete(faction);

        verify(factionRepository, times(1)).delete(faction);
    }
}
