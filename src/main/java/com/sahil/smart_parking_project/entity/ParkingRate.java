package com.sahil.smart_parking_project.entity;

import com.sahil.smart_parking_project.enums.VehicleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_rates")
public class ParkingRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private VehicleType vehicleType;

	@Column(nullable = false)
	private Double ratePerHour;

	@Column(nullable = false)
	private Boolean active = true;

	public synchronized Long getId() {
		return id;
	}

	public synchronized void setId(Long id) {
		this.id = id;
	}

	public synchronized VehicleType getVehicleType() {
		return vehicleType;
	}

	public synchronized void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public synchronized Double getRatePerHour() {
		return ratePerHour;
	}

	public synchronized void setRatePerHour(Double ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	public synchronized Boolean getActive() {
		return active;
	}

	public synchronized void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
	
	

}
