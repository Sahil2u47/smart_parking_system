package com.sahil.smart_parking_project.dto;

import jakarta.validation.constraints.NotBlank;

public class CancelBookingDTO {

	@NotBlank(message = "Vehicle number is required")
	private String vehicleNumber;

	public CancelBookingDTO() {
	}

	public CancelBookingDTO(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
}