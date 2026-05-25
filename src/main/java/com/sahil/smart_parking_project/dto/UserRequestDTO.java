package com.sahil.smart_parking_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 50, message = "Name must be between 3 to 50 characters")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 15, message = "Password must be between 8 to 15 characters")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=]{8,15}$", message = "Password must contain letters, numbers, and can include special characters")
	private String password;

	@NotNull(message = "Phone number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian phone number")
	private String phone;

	@NotNull(message = "Role is required")
	private Long roleId;

	public UserRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRequestDTO(
			@NotBlank(message = "Name is required") @Size(min = 3, max = 50, message = "Name must be between 3 to 50 characters") String name,
			@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
			@NotBlank(message = "Password is required") @Size(min = 8, max = 15, message = "Password must be between 8 to 15 characters") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=]{8,15}$", message = "Password must contain letters, numbers, and can include special characters") String password,
			@NotNull(message = "Phone number is required") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian phone number") String phone,
			@NotNull(message = "Role is required") Long roleId) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
