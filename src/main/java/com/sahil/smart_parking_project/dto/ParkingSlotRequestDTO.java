package com.sahil.smart_parking_project.dto;

import com.sahil.smart_parking_project.enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ParkingSlotRequestDTO {

	@NotBlank(message = "Slot number is required")
	@Pattern(regexp = "^[A-Z0-9_-]+$", message = "Slot number must be uppercase alphanumeric (e.g., A1, B-12)")
	private String slotNumber;

	@NotNull(message = "Vehicle type is required")
	private VehicleType slotType;

	public ParkingSlotRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParkingSlotRequestDTO(
			@NotBlank(message = "Slot number is required") @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Slot number must be uppercase alphanumeric (e.g., A1, B-12)") String slotNumber,
			VehicleType slotType) {
		super();
		this.slotNumber = slotNumber;
		this.slotType = slotType;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public VehicleType getSlotType() {
		return slotType;
	}

	public void setSlotType(VehicleType slotType) {
		this.slotType = slotType;
	}

}
