package com.primo.worldgen_backend.dto.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventRequestDTO {
    @NotBlank
    private String name;

    private String description;

    private int impact;

    @NotNull
    private Long factionId;
}

