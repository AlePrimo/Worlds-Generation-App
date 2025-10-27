package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.entities.Faction;

import java.util.List;

public interface FactionService {
    Faction create(Faction faction);
    Faction update(Long id, Faction faction);
    Faction findById(Long id);
    List<Faction> findAll();
    void delete(Long id);
}
