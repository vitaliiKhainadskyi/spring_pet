package com.spring_pet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication()
public class SpringPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPetApplication.class, args);
	}

}
