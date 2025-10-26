package com.primo.worldgen_backend.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa un mundo generado aleatoriamente")

public class World {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del mundo", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Nombre del mundo", example = "Zyrion")
    private String name;

    @NotNull
    @Schema(description = "Fecha de creación del mundo")
    private Instant createdAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "world_id")
    @Schema(description = "Lista de regiones que componen el mundo")
    private List<Region> regions;

    @Schema(description = "Número de ticks ejecutados en la simulación", example = "0")
    private long ticks = 0;

}
