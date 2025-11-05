package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.mappers.RegionMapper;
import com.primo.worldgen_backend.messaging.RegionEventPublisher;
import com.primo.worldgen_backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionDAO regionDAO;
    private final RegionEventPublisher publisher;
    private final RegionMapper regionMapper;
    @Override
    public Region create(Region region) {

        if (region.getFactions() == null) {
            region.setFactions(List.of());
        }
        if (region.getEvents() == null) {
            region.setEvents(List.of());
        }
        region.setAlive(true);
        Region saved = regionDAO.save(region);


        publisher.publishRegionUpdate(saved.getId(), regionMapper.toDTO(saved));

        return saved;
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
        existing.setFactions(region.getFactions());
        existing.setEvents(region.getEvents());
        existing.setAlive(region.isAlive());
        Region saved = regionDAO.save(existing);


        publisher.publishRegionUpdate(saved.getId(), regionMapper.toDTO(saved));

        return saved;
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


        RegionResponseDTO dto = RegionResponseDTO.builder()
                .id(existing.getId())
                .name("__DELETED__")
                .lat(existing.getLat())
                .lon(existing.getLon())
                .population(existing.getPopulation())
                .water(existing.getWater())
                .food(existing.getFood())
                .minerals(existing.getMinerals())
                .alive(existing.isAlive())
                
                .build();

        publisher.publishRegionUpdate(existing.getId(), dto);
    }

    @Override
    public Region findByName(String name) {
        return regionDAO.findByName(name);
    }
}

