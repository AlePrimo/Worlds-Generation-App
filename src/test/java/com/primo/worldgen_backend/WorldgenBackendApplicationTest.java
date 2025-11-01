package com.primo.worldgen_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class WorldgenBackendApplicationTest {

    @Test
    void testMainMethodRunsSpringApplication() {
        try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(WorldgenBackendApplication.class, new String[]{}))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            WorldgenBackendApplication.main(new String[]{});

            mockedSpringApplication.verify(
                    () -> SpringApplication.run(WorldgenBackendApplication.class, new String[]{}));
        }
    }
}
