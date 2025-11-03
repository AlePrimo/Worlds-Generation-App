package com.primo.worldgen_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "regions")
@Schema(description = "Representa una región dentro de un mundo")
public class Region {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la región", example = "1")
    private Long id;

    @NotBlank
    @Column(unique = true)
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    @Schema(description = "Lista de facciones en la región")
    private List<Faction> factions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    @Schema(description = "Eventos activos en la región")
    private List<Event> events;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "world_id", nullable = false)
    @Schema(description = "Mundo al que pertenece la región")
    private World world;

    @Schema(description = "Indica si la región aún está activa", example = "true")
    private boolean alive = true;



}
