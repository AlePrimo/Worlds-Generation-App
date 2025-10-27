package com.primo.worldgen_backend.dto.faction;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactionRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @DecimalMin(value = "0.0", message = "La agresión no puede ser menor que 0.0")
    @DecimalMax(value = "1.0", message = "La agresión no puede superar 1.0")
    private double aggression;

    @DecimalMin(value = "0.0", message = "El expansionismo no puede ser menor que 0.0")
    @DecimalMax(value = "1.0", message = "El expansionismo no puede superar 1.0")
    private double expansionism;

    @Min(value = 0, message = "El tamaño no puede ser negativo")
    private int size;
}

