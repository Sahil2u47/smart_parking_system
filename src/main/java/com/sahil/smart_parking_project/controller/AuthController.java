package com.sahil.smart_parking_project.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.LoginRequestDTO;
import com.sahil.smart_parking_project.dto.UserRequestDTO;
import com.sahil.smart_parking_project.dto.UserResponseDTO;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.map_struct.UserMapper;
import com.sahil.smart_parking_project.security.JwtUtils;
import com.sahil.smart_parking_project.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	private final UserMapper userMapper;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public AuthController(AuthService authService, UserMapper userMapper, AuthenticationManager authenticationManager,
			JwtUtils jwtUtils) {

		this.authService = authService;
		this.userMapper = userMapper;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUserController(@RequestBody @Valid UserRequestDTO requestDTO) {

		log.info("User registration request received for email: {}", requestDTO.getEmail());

		User user = userMapper.toUser(requestDTO);

		User user2 = authService.registerUserService(user);

		UserResponseDTO response = userMapper.toUserResponseDTO(user2);

		log.info("User registered successfully with id: {}", response.getId());

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		String token = jwtUtils.generateToken(dto.getEmail());

		log.info("User logged in successfully: {}", dto.getEmail());

		return ResponseEntity.ok(Map.of("token", token, "message", "Login successful"));
	}
}