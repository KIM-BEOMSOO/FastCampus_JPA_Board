package com.fastcampus.ch4;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ch4Application implements CommandLineRunner {

	@Autowired
	EntityManagerFactory emf;

	public static void main(String[] args){
		SpringApplication app = new SpringApplication(Ch4Application.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("emf = " + emf);
	}
}
