package com.devops.test.apibuilder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/build")
public class BuildController {
	@GetMapping("/")
	public ResponseEntity<String> build() {
		return ResponseEntity.status(HttpStatus.OK).body("Hello World!");
	}
}
