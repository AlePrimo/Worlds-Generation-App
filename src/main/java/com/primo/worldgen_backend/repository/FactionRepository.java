package com.primo.worldgen_backend.repository;

import com.primo.worldgen_backend.entities.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> {
}
