package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.dao.impl.RegionDAOImpl;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegionDAOImplTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionDAOImpl regionDAO;

    public RegionDAOImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_delegatesToRepository() {
        Region region = new Region();
        when(regionRepository.save(region)).thenReturn(region);

        Region result = regionDAO.save(region);

        assertEquals(region, result);
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void findAll_delegatesToRepository() {
        List<Region> regions = List.of(new Region(), new Region());
        when(regionRepository.findAll()).thenReturn(regions);

        List<Region> result = regionDAO.findAll();

        assertEquals(regions, result);
        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void findById_found_returnsEntity() {
        Region region = new Region();
        when(regionRepository.findById(1L)).thenReturn(Optional.of(region));

        Region result = regionDAO.findById(1L);

        assertEquals(region, result);
        verify(regionRepository, times(1)).findById(1L);
    }

    @Test
    void findById_notFound_returnsNull() {
        when(regionRepository.findById(99L)).thenReturn(Optional.empty());

        Region result = regionDAO.findById(99L);

        assertNull(result);
        verify(regionRepository, times(1)).findById(99L);
    }

    @Test
    void delete_delegatesToRepository() {
        Region region = new Region();

        regionDAO.delete(region);

        verify(regionRepository, times(1)).delete(region);
    }

    @Test
    void findByName_delegatesToRepository() {
        Region region = new Region();
        when(regionRepository.findByName("TestRegion")).thenReturn(region);

        Region result = regionDAO.findByName("TestRegion");

        assertEquals(region, result);
        verify(regionRepository, times(1)).findByName("TestRegion");
    }








}
