package com.primo.worldgen_backend.messaging;

import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorldEventPublisher {

    private final SimpMessagingTemplate simpMessagingTemplate;


    public void publishWorldUpdate(Long worldId, WorldResponseDTO dto) {
        String destination = "/topic/world." + worldId;
        simpMessagingTemplate.convertAndSend(destination, dto);
    }
}
