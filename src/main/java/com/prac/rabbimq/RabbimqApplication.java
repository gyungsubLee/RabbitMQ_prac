package com.prac.rabbimq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.prac.rabbimq.step3")
public class RabbimqApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbimqApplication.class, args);
	}
}
