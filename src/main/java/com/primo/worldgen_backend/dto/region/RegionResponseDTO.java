package com.primo.worldgen_backend.dto.region;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegionResponseDTO {
    private Long id;
    private String name;
    private Long worldId;
    private List<String> resources;
    private List<Long> factionIds;
}
