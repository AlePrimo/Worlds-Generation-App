package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.mapper.FactionMapper;
import com.primo.worldgen_backend.service.FactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactionServiceImpl implements FactionService {
    private final FactionDAO factionDAO;
    private final RegionDAO regionDAO;

    @Override
    public FactionResponseDTO create(FactionRequestDTO dto){
        Region region = regionDAO.findById(dto.getRegionId());
        Faction faction = FactionMapper.toEntity(dto, region);
        return FactionMapper.toDTO(factionDAO.save(faction));
    }

    @Override
    public List<FactionResponseDTO> getAll(){
        return factionDAO.findAll().stream().map(FactionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public FactionResponseDTO getById(Long id){
        Faction f = factionDAO.findById(id);
        return f!=null?FactionMapper.toDTO(f):null;
    }
}
