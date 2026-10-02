package com.sahil.smart_parking_project.dto;

public class ParkingVehicleTypeAnalyticsDTO {

	   private long total;
	    private long available;
	    private long occupied;
		public synchronized long getTotal() {
			return total;
		}
		public synchronized void setTotal(long total) {
			this.total = total;
		}
		public synchronized long getAvailable() {
			return available;
		}
		public synchronized void setAvailable(long available) {
			this.available = available;
		}
		public synchronized long getOccupied() {
			return occupied;
		}
		public synchronized void setOccupied(long occupied) {
			this.occupied = occupied;
		}

	    
	
}
