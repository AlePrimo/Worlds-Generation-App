package com.primo.worldgen_backend.dto.region;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para devolver datos de una región")
public class RegionResponseDTO {

    private Long id;
    private String name;
    private double lat;
    private double lon;
    private int population;
    private double water;
    private double food;
    private double minerals;
    private boolean alive;

    @Schema(description = "IDs de las facciones en esta región")
    private List<Long> factionIds;

    @Schema(description = "IDs de los eventos activos en esta región")
    private List<Long> eventIds;
}
