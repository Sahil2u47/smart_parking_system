package com.sahil.smart_parking_project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.entity.Role;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.enums.RoleType;
import com.sahil.smart_parking_project.globalException.ResourceNotFoundException;
import com.sahil.smart_parking_project.repository.RoleRepository;
import com.sahil.smart_parking_project.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUserService(User user) {

		Role role = roleRepository.findByName(RoleType.ROLE_USER)
				.orElseThrow(() -> new ResourceNotFoundException("User role not found"));

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		user.setRole(role);

		return userRepository.saveAndFlush(user);
	}
}