package com.primo.worldgen_backend.dto.faction;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FactionResponseDTO {
    private Long id;
    private String name;
    private int power;
    private Long regionId;
    private List<Long> eventIds;
}
