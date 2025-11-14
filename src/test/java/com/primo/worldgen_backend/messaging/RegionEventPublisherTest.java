package com.primo.worldgen_backend.messaging;

import com.primo.worldgen_backend.dto.events.EventResponseDTO;
import com.primo.worldgen_backend.dto.region.RegionResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegionEventPublisherTest {

    private SimpMessagingTemplate simpMessagingTemplate;
    private RegionEventPublisher publisher;

    @BeforeEach
    void setUp() {
        simpMessagingTemplate = mock(SimpMessagingTemplate.class);
        publisher = new RegionEventPublisher(simpMessagingTemplate);
    }

    @Test
    void testPublishRegionUpdate() {
        Long regionId = 10L;
        RegionResponseDTO dto = new RegionResponseDTO();

        publisher.publishRegionUpdate(regionId, dto);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<RegionResponseDTO> dtoCaptor = ArgumentCaptor.forClass(RegionResponseDTO.class);

        verify(simpMessagingTemplate, times(1))
                .convertAndSend(destinationCaptor.capture(), dtoCaptor.capture());

        assertEquals("/topic/region.10", destinationCaptor.getValue());
        assertEquals(dto, dtoCaptor.getValue());
    }

    @Test
    void testPublishRegionEvent() {
        Long regionId = 55L;
        EventResponseDTO eventDto = new EventResponseDTO();

        publisher.publishRegionEvent(regionId, eventDto);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<EventResponseDTO> dtoCaptor = ArgumentCaptor.forClass(EventResponseDTO.class);

        verify(simpMessagingTemplate, times(1))
                .convertAndSend(destinationCaptor.capture(), dtoCaptor.capture());

        assertEquals("/topic/region.55.events", destinationCaptor.getValue());
        assertEquals(eventDto, dtoCaptor.getValue());
    }

    @Test
    void testPublishRegionNotification() {
        String message = "Test notification";

        publisher.publishRegionNotification(message);

        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(simpMessagingTemplate, times(1))
                .convertAndSend(destinationCaptor.capture(), messageCaptor.capture());

        assertEquals("/topic/regions.notifications", destinationCaptor.getValue());
        assertEquals(message, messageCaptor.getValue());
    }
}
