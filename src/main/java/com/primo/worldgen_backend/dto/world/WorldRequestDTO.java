package com.primo.worldgen_backend.dto.world;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorldRequestDTO {
    @NotBlank
    private String name;
}

