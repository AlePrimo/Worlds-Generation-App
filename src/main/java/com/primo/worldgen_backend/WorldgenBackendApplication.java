package com.primo.worldgen_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorldgenBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldgenBackendApplication.class, args);
	}

}
