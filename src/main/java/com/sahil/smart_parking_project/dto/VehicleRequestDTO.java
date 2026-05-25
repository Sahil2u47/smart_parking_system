package com.sahil.smart_parking_project.dto;

import com.sahil.smart_parking_project.enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class VehicleRequestDTO {

	@NotBlank(message = "Vehicle number is required")
	@Pattern(regexp = "^[A-Z0-9-]+$", message = "Vehicle number must be uppercase")
	private String vehicleNumber;

	@NotNull(message = "Vehicle type is required")
	private VehicleType vehicleType;

	@NotBlank(message = "Brand is required")
	private String brand;

	@NotBlank(message = "Color is required")
	private String color;

	public VehicleRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleRequestDTO(
			@NotBlank(message = "Vehicle number is required") @Pattern(regexp = "^[A-Z0-9-]+$", message = "Vehicle number must be uppercase") String vehicleNumber,
			VehicleType vehicleType, @NotBlank(message = "Brand is required") String brand,
			@NotBlank(message = "Color is required") String color) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.vehicleType = vehicleType;
		this.brand = brand;
		this.color = color;
	}

	public synchronized String getVehicleNumber() {
		return vehicleNumber;
	}

	public synchronized void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public synchronized VehicleType getVehicleType() {
		return vehicleType;
	}

	public synchronized void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public synchronized String getBrand() {
		return brand;
	}

	public synchronized void setBrand(String brand) {
		this.brand = brand;
	}

	public synchronized String getColor() {
		return color;
	}

	public synchronized void setColor(String color) {
		this.color = color;
	}

}
