package com.sahil.smart_parking_project.dto;

import com.sahil.smart_parking_project.enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BookingEntryDTO {

	@NotBlank(message = "Vehicle number is required")
	@Pattern(regexp = "^[A-Z0-9-]+$", message = "Vehicle number must be uppercase")
	private String vehicleNumber;

	@NotNull(message = "Vehicle type is required")
	private VehicleType vehicleType;

	@NotBlank(message = "Slot number is required")
	private String slotNumber;

	// NEW FIELDS
	private String brand;

	private String color;

	public BookingEntryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookingEntryDTO(
			@NotBlank(message = "Vehicle number is required") @Pattern(regexp = "^[A-Z0-9-]+$", message = "Vehicle number must be uppercase") String vehicleNumber,
			VehicleType vehicleType, @NotBlank(message = "Slot number is required") String slotNumber, String brand,
			String color) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.vehicleType = vehicleType;
		this.slotNumber = slotNumber;
		this.brand = brand;
		this.color = color;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
