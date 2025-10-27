package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import java.util.List;

public interface FactionService {
    FactionResponseDTO create(FactionRequestDTO dto);
    List<FactionResponseDTO> getAll();
    FactionResponseDTO getById(Long id);
}
