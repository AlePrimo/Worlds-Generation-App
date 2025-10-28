package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.impl.FactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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

    @Test
    void update_existing_updatesFields() {
        Faction existing = Faction.builder().id(1L).name("Old").aggression(0.2).expansionism(0.3).size(5).build();
        Faction update = Faction.builder().name("New").aggression(0.9).expansionism(0.8).size(10).build();

        when(factionDAO.findById(1L)).thenReturn(existing);
        when(factionDAO.save(any(Faction.class))).thenAnswer(i -> i.getArgument(0));

        Faction res = factionService.update(1L, update);
        assertEquals("New", res.getName());
        assertEquals(0.9, res.getAggression());
    }

    @Test
    void delete_existing_deletes() {
        Faction existing = Faction.builder().id(2L).name("F").build();
        when(factionDAO.findById(2L)).thenReturn(existing);
        doNothing().when(factionDAO).delete(existing);

        factionService.delete(2L);
        verify(factionDAO, times(1)).delete(existing);
    }

    @Test
    void findById_notFound_throws() {
        when(factionDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> factionService.findById(99L));
        assertTrue(ex.getMessage().contains("Faction not found"));
    }
}
