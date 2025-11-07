package com.primo.worldgen_backend.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "events")
@Schema(description = "Representa un evento que afecta a una región")

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del evento", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Tipo de evento", example = "SEQUIA")
    private String type;

    @NotNull
    @Schema(description = "Fecha de inicio del evento")
    private Instant startedAt;

    @NotBlank
    @Schema(description = "Descripción del evento", example = "Sequía severa que reduce la población")
    private String description;

    @Min(1)
    @Max(10)
    @Schema(description = "Severidad del evento (1-10)", example = "7")
    private int severity;

    @Schema(description = "Indica si el evento está activo", example = "true")
    private boolean active = true;

    @Schema(description = "Region donde tuvo lugar el evento", example = "Catan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;


}
