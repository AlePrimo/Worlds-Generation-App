package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.WorldMapper;
import com.primo.worldgen_backend.messaging.WorldEventPublisher;
import com.primo.worldgen_backend.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {

    private final WorldDAO worldDAO;
    private final WorldEventPublisher publisher; // nuevo
    private final WorldMapper worldMapper;
    @Override
    public World create(World world) {


        World existing = worldDAO.findByName(world.getName());
        if (existing != null) {
            throw new RuntimeException("World already exists with name " + world.getName());
        }

        if (world.getCreatedAt() == null) {
            world.setCreatedAt(Instant.now());
        }

        world.setTicks(0);
        World saved = worldDAO.save(world);
        publisher.publishWorldUpdate(saved.getId(), worldMapper.toDTO(saved));

        return saved;
    }

    @Override
    public World update(Long id, World world) {
        World existing = findById(id);

        existing.setName(world.getName());
        existing.setRegions(world.getRegions());
        existing.setTicks(world.getTicks());
        World saved = worldDAO.save(existing);
        publisher.publishWorldUpdate(saved.getId(), worldMapper.toDTO(saved));
        return saved;

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

        WorldResponseDTO dto = WorldResponseDTO.builder()
                .id(existing.getId())
                .name("__DELETED__")
                .ticks(existing.getTicks())
                .createdAt(existing.getCreatedAt())
                .build();
        publisher.publishWorldUpdate(existing.getId(), dto);
    }

    @Override
    public World findByName(String name) {
        World w = worldDAO.findByName(name);
        if (w == null) {
            throw new RuntimeException("World not found with name " + name);
        }
        return w;
    }
}
