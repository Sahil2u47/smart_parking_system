package com.sahil.smart_parking_project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.LoginRequestDTO;
import com.sahil.smart_parking_project.entity.Role;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.repository.RoleRepository;
import com.sahil.smart_parking_project.repository.UserRepository;
import com.sahil.smart_parking_project.security.JwtUtils;

@Service
public class AuthService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtils jwtUtils;

	public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			JwtUtils jwtUtils) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}

	public User registerUserService(User user) {

		Role role = roleRepository.findById(user.getRole().getId())
				.orElseThrow(() -> new RuntimeException("Role not found"));

		user.setRole(role); // ✅ full object with name

		return userRepository.saveAndFlush(user);
	}

	public String login(LoginRequestDTO dto) {

		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		return jwtUtils.generateToken(user.getEmail());
	}

}
