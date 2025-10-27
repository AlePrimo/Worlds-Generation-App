package com.primo.worldgen_backend.dao.impl;

import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegionDAOImpl implements RegionDAO {

    private final RegionRepository regionRepository;

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public Region findById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }
}
