package com.devops.test.apibuilder.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/build")
public class BuildController {
	@GetMapping("/")
	public ResponseEntity<InputStreamResource> build(@RequestParam(required = false) String token) {
		if (token == null) {
			token = "XKWZG8PGLGKYn24QtxsY";
		}
		
		HttpURLConnection connection = null;
		
		try {
			//Create connection
		    URL url = new URL("http://builder.localhost.com/job/api-builder/build?token=" + token);
		    connection = (HttpURLConnection) url.openConnection();
		    InputStream response = connection.getInputStream();
		    
		    InputStreamResource inputStreamResponse = new InputStreamResource(response);
			return ResponseEntity.status(HttpStatus.OK).body(inputStreamResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new InputStreamResource(InputStream.nullInputStream()));
		}
	}
}
