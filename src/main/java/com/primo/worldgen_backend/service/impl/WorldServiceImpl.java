package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.dto.world.WorldRequestDTO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mapper.WorldMapper;
import com.primo.worldgen_backend.service.WorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final WorldDAO worldDAO;

    @Override
    public WorldResponseDTO create(WorldRequestDTO dto){
        World w = WorldMapper.toEntity(dto);
        w.setCreatedAt(Instant.now());
        return WorldMapper.toDTO(worldDAO.save(w));
    }

    @Override
    public List<WorldResponseDTO> getAll(){
        return worldDAO.findAll().stream().map(WorldMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public WorldResponseDTO getById(Long id){
        World w = worldDAO.findById(id);
        return w!=null?WorldMapper.toDTO(w):null;
    }
}
