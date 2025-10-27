package com.primo.worldgen_backend.dto.faction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactionResponseDTO {
    private Long id;
    private String name;
    private double aggression;
    private double expansionism;
    private int size;
}
