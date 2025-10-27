package com.primo.worldgen_backend.dto.faction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FactionRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private Long regionId;

    private int power;
}

