package com.primo.worldgen_backend.controllers;


import com.primo.worldgen_backend.dto.region.RegionRequestDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.mappers.RegionMapper;
import com.primo.worldgen_backend.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Regions", description = "APIs para manejar regiones")
public class RegionController {


    private final RegionService regionService;
    private final RegionMapper regionMapper;


    @PostMapping
    @Operation(summary = "Crear una región")
    public ResponseEntity<RegionResponseDTO> create(@Valid @RequestBody RegionRequestDTO dto) {
        Region region = regionMapper.toEntity(dto);
        Region saved = regionService.create(region);
        return new ResponseEntity<>(regionMapper.toDTO(saved), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener región por id")
    public ResponseEntity<RegionResponseDTO> getById(@PathVariable Long id) {
        Region region = regionService.findById(id);
        return ResponseEntity.ok(regionMapper.toDTO(region));
    }


    @GetMapping
    @Operation(summary = "Listar regiones")
    public ResponseEntity<List<RegionResponseDTO>> list() {
        List<RegionResponseDTO> list = regionService.findAll()
                .stream()
                .map(regionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar región")
    public ResponseEntity<RegionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RegionRequestDTO dto) {
        Region region = regionMapper.toEntity(dto);
        Region updated = regionService.update(id, region);
        return ResponseEntity.ok(regionMapper.toDTO(updated));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar región")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}