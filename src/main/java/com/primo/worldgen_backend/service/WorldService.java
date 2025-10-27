package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dto.world.WorldRequestDTO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import java.util.List;

public interface WorldService {
    WorldResponseDTO create(WorldRequestDTO dto);
    List<WorldResponseDTO> getAll();
    WorldResponseDTO getById(Long id);
}
