package com.sahil.smart_parking_project.dto;

public class ParkingBookingAnalyticsResponseDTO {

	   private long totalBookings;
	    private long activeBookings;
	    private long completedBookings;
	    private long cancelledBookings;
	    private double totalRevenue;
	    
		public synchronized long getTotalBookings() {
			return totalBookings;
		}
		public synchronized void setTotalBookings(long totalBookings) {
			this.totalBookings = totalBookings;
		}
		public synchronized long getActiveBookings() {
			return activeBookings;
		}
		public synchronized void setActiveBookings(long activeBookings) {
			this.activeBookings = activeBookings;
		}
		public synchronized long getCompletedBookings() {
			return completedBookings;
		}
		public synchronized void setCompletedBookings(long completedBookings) {
			this.completedBookings = completedBookings;
		}
		public synchronized long getCancelledBookings() {
			return cancelledBookings;
		}
		public synchronized void setCancelledBookings(long cancelledBookings) {
			this.cancelledBookings = cancelledBookings;
		}
		public synchronized double getTotalRevenue() {
			return totalRevenue;
		}
		public synchronized void setTotalRevenue(double totalRevenue) {
			this.totalRevenue = totalRevenue;
		}
	    
	    
	
}
