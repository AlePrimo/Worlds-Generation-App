package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionDAO regionDAO;

    @Override
    public Region create(Region region) {
        if (region.getFactions() == null) {
            region.setFactions(List.of());
        }
        if (region.getEvents() == null) {
            region.setEvents(List.of());
        }
        region.setAlive(true);
        return regionDAO.save(region);
    }

    @Override
    public Region update(Long id, Region region) {
        Region existing = findById(id);

        existing.setName(region.getName());
        existing.setLat(region.getLat());
        existing.setLon(region.getLon());
        existing.setPopulation(region.getPopulation());
        existing.setWater(region.getWater());
        existing.setFood(region.getFood());
        existing.setMinerals(region.getMinerals());
        existing.setAlive(region.isAlive());

        return regionDAO.save(existing);
    }

    @Override
    public Region findById(Long id) {
        Region region = regionDAO.findById(id);
        if (region == null) {
            throw new RuntimeException("Region not found with id " + id);
        }
        return region;
    }

    @Override
    public List<Region> findAll() {
        return regionDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        Region existing = findById(id);
        regionDAO.delete(existing);
    }

    @Override
    public Region findByName(String name) {
        return regionDAO.findByName(name);
    }


    public Region saveInternal(Region region) {
        return regionDAO.save(region);
    }
}

