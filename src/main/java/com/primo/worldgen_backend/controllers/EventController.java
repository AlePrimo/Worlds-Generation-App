package com.primo.worldgen_backend.controllers;
import com.primo.worldgen_backend.dto.events.EventRequestDTO;
import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.entities.Event;
import com.primo.worldgen_backend.mappers.EventMapper;
import com.primo.worldgen_backend.service.EventService;
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
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Validated
@Tag(name = "Events", description = "APIs para manejar eventos")
public class EventController {


    private final EventService eventService;
    private final EventMapper eventMapper;


    @PostMapping
    @Operation(summary = "Crear un evento")
    public ResponseEntity<EventResponseDTO> create(@Valid @RequestBody EventRequestDTO dto) {
        Event event = eventMapper.toEntity(dto);
        Event saved = eventService.create(event);
        return new ResponseEntity<>(eventMapper.toDTO(saved), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener evento por id")
    public ResponseEntity<EventResponseDTO> getById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok(eventMapper.toDTO(event));
    }


    @GetMapping
    @Operation(summary = "Listar eventos")
    public ResponseEntity<List<EventResponseDTO>> list() {
        List<EventResponseDTO> list = eventService.findAll()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evento")
    public ResponseEntity<EventResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EventRequestDTO dto) {
        Event event = eventMapper.toEntity(dto);
        Event updated = eventService.update(id, event);
        return ResponseEntity.ok(eventMapper.toDTO(updated));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}