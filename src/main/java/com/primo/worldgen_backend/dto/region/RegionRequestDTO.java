package com.primo.worldgen_backend.dto.region;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegionRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private Long worldId;

    private List<String> resources;
}

