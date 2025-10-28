package com.primo.worldgen_backend.service;


import com.primo.worldgen_backend.dao.FactionDAO;
import com.primo.worldgen_backend.dao.RegionDAO;
import com.primo.worldgen_backend.entities.Faction;
import com.primo.worldgen_backend.entities.Region;
import com.primo.worldgen_backend.service.impl.FactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class FactionServiceImplTest {


@Mock
private FactionDAO factionDAO;


@Mock
private RegionDAO regionDAO;


@InjectMocks
private FactionServiceImpl factionService;


@BeforeEach
void setup() {
MockitoAnnotations.openMocks(this);
}


@Test
void create_validValues_saves() {
Faction f = Faction.builder().name("F").aggression(0.5).expansionism(0.3).size(10).build();
when(factionDAO.save(any(Faction.class))).thenAnswer(invocation -> {
Faction arg = invocation.getArgument(0);
arg.setId(1L);
return arg;
});
when(regionDAO.findById(any(Long.class))).thenReturn(new Region());


Faction saved = factionService.create(f);
assertEquals(1L, saved.getId());
}
}