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
    void update_existing_updatesFields() {
        World existing = World.builder().id(1L).name("Old").createdAt(Instant.now()).ticks(1).build();
        World update = World.builder().name("New").ticks(10).build();
        when(worldDAO.findById(1L)).thenReturn(existing);
        when(worldDAO.save(any(World.class))).thenAnswer(invocation -> invocation.getArgument(0));

        World res = worldService.update(1L, update);
        assertEquals("New", res.getName());
        assertEquals(10, res.getTicks());
    }

    @Test
    void delete_existing_deletes() {
        World existing = World.builder().id(2L).name("X").build();
        when(worldDAO.findById(2L)).thenReturn(existing);
        doNothing().when(worldDAO).delete(existing);

        worldService.delete(2L);
        verify(worldDAO, times(1)).delete(existing);
    }

    @Test
    void findById_notFound_throws() {
        when(worldDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> worldService.findById(99L));
        assertTrue(ex.getMessage().contains("World not found"));
    }
}
