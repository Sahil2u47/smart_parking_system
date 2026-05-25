package com.sahil.smart_parking_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ExitBookingDTO {
	
	@NotBlank(message = "Vehicle number is required")
	private String vehicleNumber;

	public ExitBookingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExitBookingDTO(@NotBlank(message = "Vehicle number is required") String vehicleNumber) {
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
