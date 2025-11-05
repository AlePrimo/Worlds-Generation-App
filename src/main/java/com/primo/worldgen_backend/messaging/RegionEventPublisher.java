package com.primo.worldgen_backend.messaging;

import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RegionEventPublisher {

    private final SimpMessagingTemplate template;



    public void publishRegionUpdate(Long regionId, RegionResponseDTO dto) {

        template.convertAndSend("/topic/regions", dto);
    }
}
