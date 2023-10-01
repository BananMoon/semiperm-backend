package com.project.semipermbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SemipermBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SemipermBackendApplication.class, args);
	}

}
