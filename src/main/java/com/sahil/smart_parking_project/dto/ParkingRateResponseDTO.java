package com.sahil.smart_parking_project.dto;

import com.sahil.smart_parking_project.enums.VehicleType;

public class ParkingRateResponseDTO {

	 private Long id;
	    private VehicleType vehicleType;
	    private Double ratePerHour;
	    private Boolean active;
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
