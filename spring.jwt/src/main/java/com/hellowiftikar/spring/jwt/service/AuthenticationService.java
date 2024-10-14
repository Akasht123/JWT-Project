package com.hellowiftikar.spring.jwt.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hellowiftikar.spring.jwt.model.AuthenticationResponse;
import com.hellowiftikar.spring.jwt.model.User;
import com.hellowiftikar.spring.jwt.repository.UserRepository;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.Registration;
@Service
public class AuthenticationService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	
	
	

	public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder,
			com.hellowiftikar.spring.jwt.service.JwtService jwtService, AuthenticationManager authenticationManager) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationResponse register(User request) {
		User user =new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		user=repository.save(user);
		String token = jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}
	
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
	User user=repository.findByUsername(request.getUsername()).orElseThrow();
	String token =jwtService.generateToken(user);
	return new AuthenticationResponse(token);
	
	}
}
