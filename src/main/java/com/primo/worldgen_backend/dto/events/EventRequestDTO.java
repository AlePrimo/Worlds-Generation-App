package com.primo.worldgen_backend.dto.events;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestDTO {

    @NotBlank(message = "El tipo de evento no puede estar vacío")
    private String type;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private Instant startedAt;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @Min(value = 1, message = "La severidad mínima es 1")
    @Max(value = 10, message = "La severidad máxima es 10")
    private int severity;

    private Boolean active;
}

