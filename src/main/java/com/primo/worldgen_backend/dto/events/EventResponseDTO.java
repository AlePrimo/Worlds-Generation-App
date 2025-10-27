package com.primo.worldgen_backend.dto.events;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventResponseDTO {
    private Long id;
    private String name;
    private String description;
    private int impact;
    private Long factionId;
}
