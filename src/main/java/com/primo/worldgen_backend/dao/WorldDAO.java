package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.entities.World;
import java.util.List;

public interface WorldDAO {
    World save(World world);
    List<World> findAll();
    World findById(Long id);
    void delete(World world);
    World findByName(String name);
}
