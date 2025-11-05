package com.primo.worldgen_backend.messaging;

import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RegionEventPublisher {

    private final SimpMessagingTemplate simpMessagingTemplate;


    public void publishRegionUpdate(Long regionId, RegionResponseDTO dto) {
        String destination = "/topic/region." + regionId;
        simpMessagingTemplate.convertAndSend(destination, dto);
    }


    public void publishRegionListUpdate(List<RegionResponseDTO> dtos) {
        String destination = "/topic/regions";
        simpMessagingTemplate.convertAndSend(destination, dtos);
    }
}
