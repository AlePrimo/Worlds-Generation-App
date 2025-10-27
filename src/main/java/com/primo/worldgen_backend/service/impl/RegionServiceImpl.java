package com.primo.worldgen_backend.service.impl;


import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.repository.RegionRepository;
import com.primo.worldgen_backend.repository.WorldRepository;
import com.primo.worldgen_backend.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final WorldRepository worldRepository;

    public RegionServiceImpl(RegionRepository regionRepository, WorldRepository worldRepository) {
        this.regionRepository = regionRepository;
        this.worldRepository = worldRepository;
    }

    @Override
    public Region create(Region region) {
        validateWorld(region.getWorld().getId());
        return regionRepository.save(region);
    }

    @Override
    public Region update(Long id, Region region) {
        Region existing = findById(id);
        validateWorld(region.getWorld().getId());
        region.setId(existing.getId());
        return regionRepository.save(region);
    }

    @Override
    public Region findById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Region existing = findById(id);
        regionRepository.delete(existing);
    }

    private void validateWorld(Long worldId) {
        worldRepository.findById(worldId)
                .orElseThrow(() -> new ResourceNotFoundException("World not found with id " + worldId));
    }
}
