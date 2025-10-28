package com.primo.worldgen_backend.controllers;


import com.primo.worldgen_backend.dto.world.WorldRequestDTO;
import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.mappers.WorldMapper;
import com.primo.worldgen_backend.service.WorldService;
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
@RequestMapping("/api/worlds")
@RequiredArgsConstructor
@Validated
@Tag(name = "Worlds", description = "APIs para manejar mundos")
public class WorldController {


    private final WorldService worldService;
    private final WorldMapper worldMapper;


    @PostMapping
    @Operation(summary = "Crear un mundo")
    public ResponseEntity<WorldResponseDTO> create(@Valid @RequestBody WorldRequestDTO dto) {
        World world = worldMapper.toEntity(dto);
        World saved = worldService.create(world);
        return new ResponseEntity<>(worldMapper.toDTO(saved), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener mundo por id")
    public ResponseEntity<WorldResponseDTO> getById(@PathVariable Long id) {
        World world = worldService.findById(id);
        return ResponseEntity.ok(worldMapper.toDTO(world));
    }


    @GetMapping
    @Operation(summary = "Listar mundos")
    public ResponseEntity<List<WorldResponseDTO>> list() {
        List<WorldResponseDTO> list = worldService.findAll()
                .stream()
                .map(worldMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mundo")
    public ResponseEntity<WorldResponseDTO> update(@PathVariable Long id, @Valid @RequestBody WorldRequestDTO dto) {
        World world = worldMapper.toEntity(dto);
        World updated = worldService.update(id, world);
        return ResponseEntity.ok(worldMapper.toDTO(updated));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mundo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        worldService.delete(id);
        return ResponseEntity.noContent().build();
    }
}