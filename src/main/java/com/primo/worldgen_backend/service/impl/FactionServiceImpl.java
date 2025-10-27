package com.primo.worldgen_backend.service.impl;


import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.repository.FactionRepository;
import com.primo.worldgen_backend.repository.RegionRepository;
import com.primo.worldgen_backend.service.FactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactionServiceImpl implements FactionService {

    private final FactionRepository factionRepository;
    private final RegionRepository regionRepository;

    public FactionServiceImpl(FactionRepository factionRepository, RegionRepository regionRepository) {
        this.factionRepository = factionRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public Faction create(Faction faction) {
        validateRegion(faction.getRegion().getId());
        return factionRepository.save(faction);
    }

    @Override
    public Faction update(Long id, Faction faction) {
        Faction existing = findById(id);
        validateRegion(faction.getRegion().getId());
        faction.setId(existing.getId());
        return factionRepository.save(faction);
    }

    @Override
    public Faction findById(Long id) {
        return factionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faction not found with id " + id));
    }

    @Override
    public List<Faction> findAll() {
        return factionRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Faction existing = findById(id);
        factionRepository.delete(existing);
    }

    private void validateRegion(Long regionId) {
        regionRepository.findById(regionId)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + regionId));
    }
}
