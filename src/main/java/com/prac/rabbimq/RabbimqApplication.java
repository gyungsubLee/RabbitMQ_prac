package com.prac.rabbimq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.prac.rabbimq.step9")
public class RabbimqApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbimqApplication.class, args);
	}
}
