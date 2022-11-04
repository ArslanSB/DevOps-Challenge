package com.devops.test.apibuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ApiBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBuilderApplication.class, args);
	}

}
