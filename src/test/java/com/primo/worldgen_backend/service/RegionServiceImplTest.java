package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.impl.RegionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class RegionServiceImplTest {


@Mock
private RegionDAO regionDAO;


@InjectMocks
private RegionServiceImpl regionService;


@BeforeEach
void setup() {
MockitoAnnotations.openMocks(this);
}


@Test
void create_initializesListsAndAlive() {
Region r = Region.builder().name("R").build();
when(regionDAO.save(any(Region.class))).thenAnswer(invocation -> {
Region arg = invocation.getArgument(0);
arg.setId(1L);
return arg;
});


Region saved = regionService.create(r);
assertNotNull(saved.getFactions());
assertNotNull(saved.getEvents());
assertTrue(saved.isAlive());
assertEquals(1L, saved.getId());
}
}