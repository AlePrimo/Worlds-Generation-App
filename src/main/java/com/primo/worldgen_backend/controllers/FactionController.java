package com.primo.worldgen_backend.controllers;


import com.primo.worldgen_backend.dto.faction.FactionRequestDTO;
import com.primo.worldgen_backend.dto.faction.FactionResponseDTO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.mappers.FactionMapper;
import com.primo.worldgen_backend.service.FactionService;
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
@RequestMapping("/api/factions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Factions", description = "APIs para manejar facciones")
public class FactionController {


    private final FactionService factionService;
    private final FactionMapper factionMapper;


    @PostMapping
    @Operation(summary = "Crear una facción")
    public ResponseEntity<FactionResponseDTO> create(@Valid @RequestBody FactionRequestDTO dto) {
        Faction faction = factionMapper.toEntity(dto);
        Faction saved = factionService.create(faction);
        return new ResponseEntity<>(factionMapper.toDTO(saved), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener facción por id")
    public ResponseEntity<FactionResponseDTO> getById(@PathVariable Long id) {
        Faction faction = factionService.findById(id);
        return ResponseEntity.ok(factionMapper.toDTO(faction));
    }


    @GetMapping
    @Operation(summary = "Listar facciones")
    public ResponseEntity<List<FactionResponseDTO>> list() {
        List<FactionResponseDTO> list = factionService.findAll()
                .stream()
                .map(factionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar facción")
    public ResponseEntity<FactionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FactionRequestDTO dto) {
        Faction faction = factionMapper.toEntity(dto);
        Faction updated = factionService.update(id, faction);
        return ResponseEntity.ok(factionMapper.toDTO(updated));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar facción")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        factionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}