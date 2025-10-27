package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.FactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactionServiceImpl implements FactionService {

    private final FactionDAO factionDAO;
    private final RegionDAO regionDAO;

    @Override
    public Faction create(Faction faction) {
        if (faction.getAggression() < 0.0 || faction.getAggression() > 1.0) {
            throw new IllegalArgumentException("Aggression must be between 0.0 and 1.0");
        }
        if (faction.getExpansionism() < 0.0 || faction.getExpansionism() > 1.0) {
            throw new IllegalArgumentException("Expansionism must be between 0.0 and 1.0");
        }
        if (faction.getSize() < 0) {
            throw new IllegalArgumentException("Size must be >= 0");
        }

        if (faction.getId() != null) {
            Region region = regionDAO.findById(faction.getId());
            if (region == null) {
                throw new RuntimeException("Region not found for this faction");
            }
        }

        return factionDAO.save(faction);
    }

    @Override
    public Faction update(Long id, Faction faction) {
        Faction existing = findById(id);

        existing.setName(faction.getName());

        if (faction.getAggression() < 0.0 || faction.getAggression() > 1.0) {
            throw new IllegalArgumentException("Aggression must be between 0.0 and 1.0");
        }
        existing.setAggression(faction.getAggression());

        if (faction.getExpansionism() < 0.0 || faction.getExpansionism() > 1.0) {
            throw new IllegalArgumentException("Expansionism must be between 0.0 and 1.0");
        }
        existing.setExpansionism(faction.getExpansionism());

        if (faction.getSize() < 0) {
            throw new IllegalArgumentException("Size must be >= 0");
        }
        existing.setSize(faction.getSize());

        if (faction.getId() != null) {
            Region region = regionDAO.findById(faction.getId());
            if (region == null) {
                throw new RuntimeException("Region not found for this faction");
            }
        }

        return factionDAO.save(existing);
    }

    @Override
    public Faction findById(Long id) {
        Faction faction = factionDAO.findById(id);
        if (faction == null) {
            throw new RuntimeException("Faction not found with id " + id);
        }
        return faction;
    }

    @Override
    public List<Faction> findAll() {
        return factionDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        Faction existing = findById(id);
        factionDAO.delete(existing);
    }
}


