package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.entities.Region;
import java.util.List;

public interface RegionService {
    Region create(Region region);
    Region update(Long id, Region region);
    Region findById(Long id);
    List<Region> findAll();
    void delete(Long id);
}
