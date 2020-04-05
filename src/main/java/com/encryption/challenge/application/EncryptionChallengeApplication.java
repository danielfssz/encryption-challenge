package com.encryption.challenge.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.encryption.challenge")
@EnableAutoConfiguration
@SpringBootApplication
public class EncryptionChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncryptionChallengeApplication.class, args);
	}

}
