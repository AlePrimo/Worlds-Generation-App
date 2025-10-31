package com.primo.worldgen_backend.dto.world;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorldResponseDTO {
    private Long id;
    private String name;
    private long ticks;
    private Instant createdAt;
    private List<Long> regionIds;
}
