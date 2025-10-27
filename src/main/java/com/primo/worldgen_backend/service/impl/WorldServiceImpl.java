package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {

    private final WorldDAO worldDAO;

    @Override
    public World create(World world) {

        if (world.getCreatedAt() == null) {
            world.setCreatedAt(Instant.now());
        }

        world.setTicks(0);


        return worldDAO.save(world);
    }

    @Override
    public World update(Long id, World world) {
        World existing = findById(id);


        existing.setName(world.getName());
        existing.setRegions(world.getRegions());
        existing.setTicks(world.getTicks());


        return worldDAO.save(existing);
    }

    @Override
    public World findById(Long id) {
        World world = worldDAO.findById(id);
        if (world == null) {
            throw new RuntimeException("World not found with id " + id);
        }
        return world;
    }

    @Override
    public List<World> findAll() {
        return worldDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        World existing = findById(id);
      worldDAO.delete(existing);

    }
}
