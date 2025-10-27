package com.primo.worldgen_backend.dao;

import com.primo.worldgen_backend.entities.Faction;
import java.util.List;

public interface FactionDAO {
    Faction save(Faction faction);
    List<Faction> findAll();
    Faction findById(Long id);
}
