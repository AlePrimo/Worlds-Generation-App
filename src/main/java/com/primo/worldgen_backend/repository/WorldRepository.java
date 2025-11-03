package com.primo.worldgen_backend.repository;

import com.primo.worldgen_backend.entities.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorldRepository extends JpaRepository<World, Long> {
    Optional<World> findByName(String name);
}
