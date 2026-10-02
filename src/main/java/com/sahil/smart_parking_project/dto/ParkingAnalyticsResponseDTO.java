package com.sahil.smart_parking_project.dto;

import java.util.Map;

public class ParkingAnalyticsResponseDTO {

	 private long totalSlots;
	    private long availableSlots;
	    private long occupiedSlots;
	    private long maintenanceSlots;
	    private double occupancyPercentage;
	    private Map<String, ParkingVehicleTypeAnalyticsDTO> vehicleTypeAnalytics;
	    
	    
		public synchronized long getTotalSlots() {
			return totalSlots;
		}
		public synchronized void setTotalSlots(long totalSlots) {
			this.totalSlots = totalSlots;
		}
		public synchronized long getAvailableSlots() {
			return availableSlots;
		}
		public synchronized void setAvailableSlots(long availableSlots) {
			this.availableSlots = availableSlots;
		}
		public synchronized long getOccupiedSlots() {
			return occupiedSlots;
		}
		public synchronized void setOccupiedSlots(long occupiedSlots) {
			this.occupiedSlots = occupiedSlots;
		}
		public synchronized long getMaintenanceSlots() {
			return maintenanceSlots;
		}
		public synchronized void setMaintenanceSlots(long maintenanceSlots) {
			this.maintenanceSlots = maintenanceSlots;
		}
		public synchronized double getOccupancyPercentage() {
			return occupancyPercentage;
		}
		public synchronized void setOccupancyPercentage(double occupancyPercentage) {
			this.occupancyPercentage = occupancyPercentage;
		}
		public synchronized Map<String, ParkingVehicleTypeAnalyticsDTO> getVehicleTypeAnalytics() {
			return vehicleTypeAnalytics;
		}
		public synchronized void setVehicleTypeAnalytics(Map<String, ParkingVehicleTypeAnalyticsDTO> vehicleTypeAnalytics) {
			this.vehicleTypeAnalytics = vehicleTypeAnalytics;
		}
	    
		
	    
	    
	
}
