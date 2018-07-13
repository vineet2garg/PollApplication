package com.springboot.microservice.poll.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.microservice.poll.exception.AppException;
import com.springboot.microservice.poll.model.Role;
import com.springboot.microservice.poll.model.RoleName;
import com.springboot.microservice.poll.model.User;
import com.springboot.microservice.poll.payload.ApiResponse;
import com.springboot.microservice.poll.payload.JwtAuthenticationResponse;
import com.springboot.microservice.poll.payload.LoginRequest;
import com.springboot.microservice.poll.payload.SignUpRequest;
import com.springboot.microservice.poll.repository.RoleRepository;
import com.springboot.microservice.poll.repository.UserRepository;
import com.springboot.microservice.poll.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	public AuthController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping(path = "/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

		SecurityContextHolder.getContext()
				.setAuthentication(authenticate);

		String jwtToken = tokenProvider.generateToken(authenticate);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
	}

	@PostMapping(path = "/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest request) {

		if (userRepository.existsByUsername(request.getUsername())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false, "Username is already exist!"));
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false, "Email is already exist!"));
		}

		// Create an User
		User user = new User(request.getName(), request.getUsername(), request.getEmail(), request.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role is not set"));
		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("api/auth/{userName}")
				.buildAndExpand(result.getUsername())
				.toUri();

		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "User successfully registered"));
	}

}
