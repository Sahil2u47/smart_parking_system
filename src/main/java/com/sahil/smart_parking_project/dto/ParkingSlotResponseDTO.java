package com.sahil.smart_parking_project.dto;

import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.enums.VehicleType;

public class ParkingSlotResponseDTO {

	private Long id;
	private String slotNumber;
	private SlotStatus status;
	private VehicleType slotType;

	public ParkingSlotResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParkingSlotResponseDTO(Long id, String slotNumber, SlotStatus status, VehicleType slotType) {
		super();
		this.id = id;
		this.slotNumber = slotNumber;
		this.status = status;
		this.slotType = slotType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public SlotStatus getStatus() {
		return status;
	}

	public void setStatus(SlotStatus status) {
		this.status = status;
	}

	public VehicleType getSlotType() {
		return slotType;
	}

	public void setSlotType(VehicleType slotType) {
		this.slotType = slotType;
	}

}
