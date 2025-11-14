package com.primo.worldgen_backend.messaging;

import com.primo.worldgen_backend.dto.world.WorldResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WorldEventPublisherTest {

    private SimpMessagingTemplate simpMessagingTemplate;
    private WorldEventPublisher publisher;

    @BeforeEach
    void setUp() {
        simpMessagingTemplate = mock(SimpMessagingTemplate.class);
        publisher = new WorldEventPublisher(simpMessagingTemplate);
    }

    @Test
    void testPublishWorldUpdate() {
        Long worldId = 7L;
        WorldResponseDTO dto = new WorldResponseDTO();

        publisher.publishWorldUpdate(worldId, dto);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<WorldResponseDTO> dtoCaptor = ArgumentCaptor.forClass(WorldResponseDTO.class);

        verify(simpMessagingTemplate, times(1))
                .convertAndSend(destinationCaptor.capture(), dtoCaptor.capture());

        assertEquals("/topic/world.7", destinationCaptor.getValue());
        assertEquals(dto, dtoCaptor.getValue());
    }

    @Test
    void testPublishWorldListUpdate() {
        List<WorldResponseDTO> list = List.of(new WorldResponseDTO(), new WorldResponseDTO());

        publisher.publishWorldListUpdate(list);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List<WorldResponseDTO>> listCaptor = ArgumentCaptor.forClass(List.class);

        verify(simpMessagingTemplate, times(1))
                .convertAndSend(destinationCaptor.capture(), listCaptor.capture());

        assertEquals("/topic/worlds", destinationCaptor.getValue());
        assertEquals(list, listCaptor.getValue());
    }

    @Test
    void testPublishWorldTick() {
        Long worldId = 99L;
        long tickCount = 12345L;

        publisher.publishWorldTick(worldId, tickCount);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> tickCaptor = ArgumentCaptor.forClass(Long.class);

        verify(simpMessagingTemplate, times(1))
                .convertAndSend(destinationCaptor.capture(), tickCaptor.capture());

        assertEquals("/topic/world.99.tick", destinationCaptor.getValue());
        assertEquals(tickCount, tickCaptor.getValue());
    }
}
