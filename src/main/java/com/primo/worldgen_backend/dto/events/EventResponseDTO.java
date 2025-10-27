package com.primo.worldgen_backend.dto.events;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseDTO {
    private Long id;
    private String type;
    private Instant startedAt;
    private String description;
    private int severity;
    private boolean active;
}
