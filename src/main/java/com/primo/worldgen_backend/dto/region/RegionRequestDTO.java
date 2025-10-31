package com.primo.worldgen_backend.dto.region;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear o actualizar una región")
public class RegionRequestDTO {

    @NotBlank
    @Schema(description = "Nombre de la región", example = "Bosque Norte")
    private String name;

    @Schema(description = "Latitud geográfica", example = "-34.56")
    private double lat;

    @Schema(description = "Longitud geográfica", example = "-58.45")
    private double lon;

    @Min(0)
    @Schema(description = "Población de la región", example = "500")
    private int population;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(description = "Cantidad de agua disponible (0-100)", example = "75.5")
    private double water;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(description = "Cantidad de alimento disponible (0-100)", example = "60.0")
    private double food;

    @DecimalMin("0.0")
    @Schema(description = "Cantidad de minerales disponibles", example = "120.0")
    private double minerals;

    @Schema(description = "Indica si la región está activa", example = "true")
    private boolean alive;
}
