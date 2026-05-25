package com.sahil.smart_parking_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class LoginRequestDTO {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public LoginRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginRequestDTO(@Email @NotBlank String email, @NotBlank String password) {
		super();
		this.email = email;
		this.password = password;
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
	
	
	
}
