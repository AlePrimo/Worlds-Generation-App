package com.primo.worldgen_backend.service;

import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import java.util.List;

public interface RegionService {
    RegionResponseDTO create(RegionRequestDTO dto);
    List<RegionResponseDTO> getAll();
    RegionResponseDTO getById(Long id);
}
