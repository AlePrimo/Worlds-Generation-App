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
    void update_existing_updatesFields() {
        Region existing = Region.builder().id(1L).name("Old").build();
        Region update = Region.builder().name("New").lat(1.1).lon(2.2).population(5).build();

        when(regionDAO.findById(1L)).thenReturn(existing);
        when(regionDAO.save(any(Region.class))).thenAnswer(i -> i.getArgument(0));

        Region res = regionService.update(1L, update);
        assertEquals("New", res.getName());
        assertEquals(1.1, res.getLat());
    }

    @Test
    void delete_existing_deletes() {
        Region existing = Region.builder().id(2L).name("R").build();
        when(regionDAO.findById(2L)).thenReturn(existing);
        doNothing().when(regionDAO).delete(existing);

        regionService.delete(2L);
        verify(regionDAO, times(1)).delete(existing);
    }

    @Test
    void findById_notFound_throws() {
        when(regionDAO.findById(99L)).thenReturn(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> regionService.findById(99L));
        assertTrue(ex.getMessage().contains("Region not found"));
    }
}
