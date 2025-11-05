package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.entities.Region;
import java.util.List;

public interface RegionDAO {
    Region save(Region region);
    List<Region> findAll();
    Region findById(Long id);
    void delete(Region region);
    Region findByName(String name);
}
