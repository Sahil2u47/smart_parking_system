package com.sahil.smart_parking_project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class ParkingRateRequestDTO {

	 @NotNull(message = "Rate per hour is required")
	    @DecimalMin(value = "0.01", message = "Parking rate must be greater than 0")
	    private Double ratePerHour;

	    public Double getRatePerHour() {
	        return ratePerHour;
	    }

	    public void setRatePerHour(Double ratePerHour) {
	        this.ratePerHour = ratePerHour;
	    }
	
}
