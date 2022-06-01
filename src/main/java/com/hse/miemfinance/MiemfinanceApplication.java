package com.hse.miemfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MiemfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiemfinanceApplication.class, args);
	}

}
