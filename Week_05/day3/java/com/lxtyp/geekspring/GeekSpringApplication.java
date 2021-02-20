package com.lxtyp.geekspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lxtyp")
public class GeekSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekSpringApplication.class, args);
	}
}
