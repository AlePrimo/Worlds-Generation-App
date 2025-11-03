package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.dao.impl.WorldDAOImpl;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.repository.WorldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorldDAOImplTest {

    @Mock
    private WorldRepository worldRepository;

    @InjectMocks
    private WorldDAOImpl worldDAO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_delegatesToRepository() {
        World world = new World();
        when(worldRepository.save(world)).thenReturn(world);

        World result = worldDAO.save(world);

        assertEquals(world, result);
        verify(worldRepository).save(world);
    }

    @Test
    void findAll_delegatesToRepository() {
        List<World> worlds = List.of(new World(), new World());
        when(worldRepository.findAll()).thenReturn(worlds);

        List<World> result = worldDAO.findAll();

        assertEquals(worlds, result);
        verify(worldRepository).findAll();
    }

    @Test
    void findById_found_returnsEntity() {
        World world = new World();
        when(worldRepository.findById(1L)).thenReturn(Optional.of(world));

        World result = worldDAO.findById(1L);

        assertEquals(world, result);
        verify(worldRepository).findById(1L);
    }

    @Test
    void findById_notFound_returnsNull() {
        when(worldRepository.findById(99L)).thenReturn(Optional.empty());

        World result = worldDAO.findById(99L);

        assertNull(result);
        verify(worldRepository).findById(99L);
    }

    @Test
    void delete_delegatesToRepository() {
        World world = new World();

        worldDAO.delete(world);

        verify(worldRepository).delete(world);
    }

    @Test
    void findByName_found_returnsEntity() {
        World world = new World();
        when(worldRepository.findByName("Earth")).thenReturn(Optional.of(world));

        World result = worldDAO.findByName("Earth");

        assertEquals(world, result);
        verify(worldRepository).findByName("Earth");
    }

    @Test
    void findByName_notFound_returnsNull() {
        when(worldRepository.findByName("Unknown")).thenReturn(Optional.empty());

        World result = worldDAO.findByName("Unknown");

        assertNull(result);
        verify(worldRepository).findByName("Unknown");
    }
}
