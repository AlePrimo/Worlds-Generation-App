package com.primo.worldgen_backend.entities;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa una facción dentro de una región")
@Table(name = "factions")
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la facción", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Nombre de la facción", example = "Clan del Norte")
    private String name;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @Schema(description = "Nivel de agresión (0.0 - 1.0)", example = "0.7")
    private double aggression;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @Schema(description = "Nivel de expansionismo (0.0 - 1.0)", example = "0.5")
    private double expansionism;

    @Min(0)
    @Schema(description = "Tamaño de la facción (población controlada)", example = "120")
    private int size;
}
