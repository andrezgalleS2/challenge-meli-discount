package com.technical.test.meli.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChallengeMeliDiscountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeMeliDiscountApplication.class, args);
	}

}
