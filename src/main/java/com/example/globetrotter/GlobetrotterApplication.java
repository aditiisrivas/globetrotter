package com.example.globetrotter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.example.globetrotter.repository")
public class GlobetrotterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobetrotterApplication.class, args);
	}

}
