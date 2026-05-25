package com.sahil.smart_parking_project.entity;

import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.enums.VehicleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ParkingSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String slotNumber;

	@Enumerated(EnumType.STRING)
	private SlotStatus status;

	@Enumerated(EnumType.STRING)
	private VehicleType slotType;

	public ParkingSlot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParkingSlot(Long id, String slotNumber, SlotStatus status, VehicleType slotType) {
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
