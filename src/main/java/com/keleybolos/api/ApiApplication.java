package com.keleybolos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);

		// 🔐 gera senha criptografada para "123"
		System.out.println(
				new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("123")
		);
	}
}