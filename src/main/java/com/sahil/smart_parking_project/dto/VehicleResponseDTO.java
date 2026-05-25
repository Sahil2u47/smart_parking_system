package com.sahil.smart_parking_project.dto;

import com.sahil.smart_parking_project.enums.VehicleType;

public class VehicleResponseDTO {

	private Long id;

	private String vehicleNumber;

	private VehicleType vehicleType;

	private String brand;

	private String color;

	private Long userId;

	private String userName;

	private String userEmail;

	public VehicleResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleResponseDTO(Long id, String vehicleNumber, VehicleType vehicleType, String brand, String color,
			Long userId, String userName, String userEmail) {
		super();
		this.id = id;
		this.vehicleNumber = vehicleNumber;
		this.vehicleType = vehicleType;
		this.brand = brand;
		this.color = color;
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
	}

	public synchronized Long getId() {
		return id;
	}

	public synchronized void setId(Long id) {
		this.id = id;
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

	public synchronized Long getUserId() {
		return userId;
	}

	public synchronized void setUserId(Long userId) {
		this.userId = userId;
	}

	public synchronized String getUserName() {
		return userName;
	}

	public synchronized void setUserName(String userName) {
		this.userName = userName;
	}

	public synchronized String getUserEmail() {
		return userEmail;
	}

	public synchronized void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
