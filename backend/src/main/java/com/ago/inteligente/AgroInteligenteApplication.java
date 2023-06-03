package com.ago.inteligente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
		basePackages = {
				"com.ago.inteligente.User.Repository"
		}
)
public class AgroInteligenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroInteligenteApplication.class, args);
	}
}
