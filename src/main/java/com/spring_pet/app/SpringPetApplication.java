package com.spring_pet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPetApplication.class, args);
	}

}
