package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.dao.WorldDAO;
import com.primo.worldgen_backend.entities.World;
import com.primo.worldgen_backend.service.impl.WorldServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.Instant;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class WorldServiceImplTest {


@Mock
private WorldDAO worldDAO;


@InjectMocks
private WorldServiceImpl worldService;


@BeforeEach
void setup() {
MockitoAnnotations.openMocks(this);
}


@Test
void create_setsCreatedAtAndTicks() {
World w = World.builder().name("W").build();
when(worldDAO.save(any(World.class))).thenAnswer(invocation -> {
World arg = invocation.getArgument(0);
arg.setId(1L);
return arg;
});


World saved = worldService.create(w);
assertNotNull(saved.getCreatedAt());
assertEquals(0, saved.getTicks());
assertEquals(1L, saved.getId());
}
}