package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.entities.World;

import java.util.List;

public interface WorldService {
    World create(World world);
    World update(Long id, World world);
    World findById(Long id);
    List<World> findAll();
    void delete(Long id);
}

