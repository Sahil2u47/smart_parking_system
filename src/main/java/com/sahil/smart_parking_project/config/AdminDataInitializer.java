package com.sahil.smart_parking_project.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sahil.smart_parking_project.entity.Role;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.enums.RoleType;
import com.sahil.smart_parking_project.repository.RoleRepository;
import com.sahil.smart_parking_project.repository.UserRepository;

@Component
public class AdminDataInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminDataInitializer(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {

		// Create roles if they don't exist
		Role userRole = getOrCreateRole(RoleType.ROLE_USER);
		Role adminRole = getOrCreateRole(RoleType.ROLE_ADMIN);

		// Create default admins
		createAdmin("admin1@smartparking.com", "Admin One", "Admin@123","9988776655", adminRole);

		createAdmin("admin2@smartparking.com", "Admin Two", "Admin@123", "9988776656" , adminRole);

		createAdmin("admin3@smartparking.com", "Admin Three", "Admin@123", "9988776657", adminRole);

		createAdmin("admin4@smartparking.com", "Admin Four", "Admin@123", "9988776658" , adminRole);
	}

	private Role getOrCreateRole(RoleType roleType) {

		return roleRepository.findByName(roleType).orElseGet(() -> {

			Role role = new Role();
			role.setName(roleType);

			return roleRepository.save(role);
		});
	}

	private void createAdmin(String email, String name, String password,    String phone , Role adminRole) {

		if (userRepository.findByEmail(email).isEmpty()) {

			User admin = new User();

			admin.setName(name);
			admin.setEmail(email);
			admin.setPassword(passwordEncoder.encode(password));
			admin.setPhone(phone);
			admin.setRole(adminRole);

			userRepository.save(admin);

			System.out.println("Admin created: " + email);
		}
	}
}