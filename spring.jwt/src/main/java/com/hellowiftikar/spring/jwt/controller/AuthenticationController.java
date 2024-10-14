package com.hellowiftikar.spring.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hellowiftikar.spring.jwt.model.AuthenticationResponse;
import com.hellowiftikar.spring.jwt.model.User;
import com.hellowiftikar.spring.jwt.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	private final AuthenticationService authService;

	public AuthenticationController(AuthenticationService authService) {
		super();
		this.authService = authService;
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
		
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
		
		return ResponseEntity.ok(authService.register(request));
	}


}
