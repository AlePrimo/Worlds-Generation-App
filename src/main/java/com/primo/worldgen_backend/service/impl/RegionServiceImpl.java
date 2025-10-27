package com.primo.worldgen_backend.service.impl;

import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mapper.RegionMapper;
import com.primo.worldgen_backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionDAO regionDAO;
    private final WorldDAO worldDAO;

    @Override
    public RegionResponseDTO create(RegionRequestDTO dto){
        World world = worldDAO.findById(dto.getWorldId());
        Region region = RegionMapper.toEntity(dto, world);
        return RegionMapper.toDTO(regionDAO.save(region));
    }

    @Override
    public List<RegionResponseDTO> getAll(){
        return regionDAO.findAll().stream().map(RegionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RegionResponseDTO getById(Long id){
        Region region = regionDAO.findById(id);
        return region!=null?RegionMapper.toDTO(region):null;
    }
}
