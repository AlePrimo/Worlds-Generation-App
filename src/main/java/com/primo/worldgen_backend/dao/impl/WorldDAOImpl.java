package com.primo.worldgen_backend.dao.impl;

import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorldDAOImpl implements WorldDAO {

    private final WorldRepository worldRepository;

    @Override
    public World save(World world) {
        return worldRepository.save(world);
    }

    @Override
    public List<World> findAll() {
        return worldRepository.findAll();
    }

    @Override
    public World findById(Long id) {
        return worldRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(World world) {
        worldRepository.delete(world);
    }

    @Override
    public World findByName(String name) {
        return worldRepository.findByName(name).orElse(null);
    }
}
