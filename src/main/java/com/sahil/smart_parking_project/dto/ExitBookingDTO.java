package com.sahil.smart_parking_project.dto;

import jakarta.validation.constraints.NotBlank;

public class ExitBookingDTO {
	
	@NotBlank(message = "Vehicle number is required")
	private String vehicleNumber;

	public ExitBookingDTO() {
		super();
	}

	public ExitBookingDTO(String vehicleNumber) {
		super();
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	

}
