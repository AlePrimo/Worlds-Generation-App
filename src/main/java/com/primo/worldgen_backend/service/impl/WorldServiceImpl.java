package com.primo.worldgen_backend.service.impl;


import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.repository.WorldRepository;
import com.primo.worldgen_backend.service.WorldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorldServiceImpl implements WorldService {

    private final WorldRepository worldRepository;

    public WorldServiceImpl(WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    @Override
    public World create(World world) {
        return worldRepository.save(world);
    }

    @Override
    public World update(Long id, World world) {
        World existing = findById(id);
        world.setId(existing.getId());
        return worldRepository.save(world);
    }

    @Override
    public World findById(Long id) {
        return worldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("World not found with id " + id));
    }

    @Override
    public List<World> findAll() {
        return worldRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        World existing = findById(id);
        worldRepository.delete(existing);
    }
}
