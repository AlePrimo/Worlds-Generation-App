package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.impl.RegionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegionServiceImplTest {

    @Mock
    private RegionDAO regionDAO;

    @InjectMocks
    private RegionServiceImpl regionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_withNullLists_setsDefaultsAndSaves() {
        Region region = Region.builder().id(1L).name("Region").factions(null).events(null).build();
        when(regionDAO.save(any(Region.class))).thenAnswer(i -> i.getArgument(0));

        Region result = regionService.create(region);

        assertTrue(result.isAlive());
        assertNotNull(result.getFactions());
        assertNotNull(result.getEvents());
        verify(regionDAO, times(1)).save(region);
    }

    @Test
    void create_withExistingLists_savesAsIs() {
        Region region = Region.builder().factions(List.of()).events(List.of()).build();
        when(regionDAO.save(any(Region.class))).thenAnswer(i -> i.getArgument(0));

        Region result = regionService.create(region);

        assertTrue(result.isAlive());
        assertEquals(List.of(), result.getFactions());
        verify(regionDAO).save(region);
    }

    @Test
    void update_existing_updatesAllFields() {
        Region existing = Region.builder().id(1L).name("Old").build();
        Region update = Region.builder()
                .name("New")
                .lat(1.1)
                .lon(2.2)
                .population(100)
                .water(50)
                .food(60)
                .minerals(70)
                .factions(List.of())
                .events(List.of())
                .alive(false)
                .build();

        when(regionDAO.findById(1L)).thenReturn(existing);
        when(regionDAO.save(any(Region.class))).thenAnswer(i -> i.getArgument(0));

        Region result = regionService.update(1L, update);

        assertEquals("New", result.getName());
        assertEquals(1.1, result.getLat());
        assertFalse(result.isAlive());
        verify(regionDAO).save(existing);
    }

    @Test
    void findById_found_returnsRegion() {
        Region region = Region.builder().id(1L).name("R").build();
        when(regionDAO.findById(1L)).thenReturn(region);

        Region result = regionService.findById(1L);

        assertEquals("R", result.getName());
        verify(regionDAO).findById(1L);
    }

    @Test
    void findById_notFound_throwsException() {
        when(regionDAO.findById(1L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> regionService.findById(1L));
        assertTrue(ex.getMessage().contains("Region not found"));
    }

    @Test
    void findAll_returnsList() {
        when(regionDAO.findAll()).thenReturn(List.of(new Region(), new Region()));

        List<Region> list = regionService.findAll();

        assertEquals(2, list.size());
        verify(regionDAO).findAll();
    }

    @Test
    void delete_existing_callsDelete() {
        Region existing = Region.builder().id(1L).build();
        when(regionDAO.findById(1L)).thenReturn(existing);
        doNothing().when(regionDAO).delete(existing);

        regionService.delete(1L);

        verify(regionDAO).delete(existing);
    }
}
