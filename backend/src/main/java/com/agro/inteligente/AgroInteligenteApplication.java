package com.agro.inteligente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
		basePackages = {
				"com.agro.inteligente.User.Repository",
				"com.agro.inteligente.Token"
		}
)
public class AgroInteligenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroInteligenteApplication.class, args);
	}
}
