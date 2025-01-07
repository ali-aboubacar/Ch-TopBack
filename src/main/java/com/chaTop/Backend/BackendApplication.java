package com.chaTop.Backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("SPRING_LOCAL_PORT", dotenv.get("SPRING_LOCAL_PORT"));
		System.setProperty("MYSQLDB_URL", dotenv.get("MYSQLDB_URL"));
		System.setProperty("MYSQLDB_USERNAME", dotenv.get("MYSQLDB_USERNAME"));
		System.setProperty("MYSQLDB_PASSWORD", dotenv.get("MYSQLDB_PASSWORD"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("hello word");
		try {
			Files.createDirectories(Paths.get("uploads"));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

}
