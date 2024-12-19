package com.chaTop.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("hello word");
		try {
			Files.createDirectories(Paths.get("uploads"));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

}
