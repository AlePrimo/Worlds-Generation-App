package com.primo.worldgen_backend.dao.impl;

import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.repository.FactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FactionDAOImpl implements FactionDAO {

    private final FactionRepository factionRepository;

    @Override
    public Faction save(Faction faction) {
        return factionRepository.save(faction);
    }

    @Override
    public List<Faction> findAll() {
        return factionRepository.findAll();
    }

    @Override
    public Faction findById(Long id) {
        return factionRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Faction faction) {
        factionRepository.delete(faction);
    }
}
