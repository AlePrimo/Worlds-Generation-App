package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.service.impl.WorldServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorldServiceImplTest {

    @Mock
    private WorldDAO worldDAO;

    @InjectMocks
    private WorldServiceImpl worldService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_withNullCreatedAt_setsNowAndTicksZero() {
        World world = World.builder().id(1L).name("W").createdAt(null).ticks(99).build();

        when(worldDAO.findByName("W")).thenReturn(null);
        when(worldDAO.save(any(World.class))).thenAnswer(i -> i.getArgument(0));

        World result = worldService.create(world);

        assertEquals(0, result.getTicks());
        assertNotNull(result.getCreatedAt());
        verify(worldDAO).findByName("W");
        verify(worldDAO).save(world);
    }

    @Test
    void create_withCreatedAt_keepsValue() {
        Instant now = Instant.now();
        World world = World.builder().name("W2").createdAt(now).build();

        when(worldDAO.findByName("W2")).thenReturn(null);
        when(worldDAO.save(any(World.class))).thenAnswer(i -> i.getArgument(0));

        World result = worldService.create(world);

        assertEquals(now, result.getCreatedAt());
        assertEquals(0, result.getTicks());
    }

    @Test
    void create_existingName_throwsException() {
        World world = World.builder().name("Earth").build();
        when(worldDAO.findByName("Earth")).thenReturn(new World());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> worldService.create(world));
        assertTrue(ex.getMessage().contains("World already exists"));
        verify(worldDAO, never()).save(any());
    }

    @Test
    void update_existing_updatesAllFields() {
        World existing = World.builder().id(1L).name("Old").ticks(1).build();
        World update = World.builder().name("New").regions(List.of()).ticks(10).build();

        when(worldDAO.findById(1L)).thenReturn(existing);
        when(worldDAO.save(any(World.class))).thenAnswer(i -> i.getArgument(0));

        World result = worldService.update(1L, update);

        assertEquals("New", result.getName());
        assertEquals(10, result.getTicks());
        verify(worldDAO).save(existing);
    }

    @Test
    void findById_found_returnsWorld() {
        World world = World.builder().id(1L).name("W").build();
        when(worldDAO.findById(1L)).thenReturn(world);

        World result = worldService.findById(1L);

        assertEquals("W", result.getName());
        verify(worldDAO).findById(1L);
    }

    @Test
    void findById_notFound_throwsException() {
        when(worldDAO.findById(1L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> worldService.findById(1L));
        assertTrue(ex.getMessage().contains("World not found"));
    }

    @Test
    void findAll_returnsList() {
        when(worldDAO.findAll()).thenReturn(List.of(new World(), new World()));

        List<World> list = worldService.findAll();

        assertEquals(2, list.size());
        verify(worldDAO).findAll();
    }

    @Test
    void delete_existing_callsDelete() {
        World existing = World.builder().id(1L).build();
        when(worldDAO.findById(1L)).thenReturn(existing);

        worldService.delete(1L);

        verify(worldDAO).delete(existing);
    }

    @Test
    void findByName_found_returnsWorld() {
        World world = World.builder().id(1L).name("Mars").build();
        when(worldDAO.findByName("Mars")).thenReturn(world);

        World result = worldService.findByName("Mars");

        assertEquals("Mars", result.getName());
        verify(worldDAO).findByName("Mars");
    }

    @Test
    void findByName_notFound_throwsException() {
        when(worldDAO.findByName("X")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> worldService.findByName("X"));
        assertTrue(ex.getMessage().contains("World not found"));
    }
}
