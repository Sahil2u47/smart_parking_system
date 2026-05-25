package com.sahil.smart_parking_project.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping(value = "/auth")
public class AuthController {

	private final AuthService authService;

	private final UserMapper userMapper;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;
	
	

	public AuthController(AuthService authService, UserMapper userMapper, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		super();
		this.authService = authService;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> registerUserController(@RequestBody @Valid UserRequestDTO requestDTO) {

		User user = userMapper.toUser(requestDTO);

		System.out.println("request = " + user);

		String pass = passwordEncoder.encode(requestDTO.getPassword());

		user.setPassword(pass);

		User user2 = authService.registerUserService(user);

		UserResponseDTO response = userMapper.toUserResponseDTO(user2);

		System.out.println("response = " + response);

		return new ResponseEntity(response, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		String token = jwtUtils.generateToken(dto.getEmail());

		return ResponseEntity.ok(Map.of("token", token, "message", "Login successful"));
	}

}
