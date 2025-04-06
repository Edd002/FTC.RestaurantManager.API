package com.fiap.tech.challenge;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@Log
@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class FTCMsUserManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FTCMsUserManagerApiApplication.class, args);
	}
}