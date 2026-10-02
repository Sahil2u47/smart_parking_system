package com.sahil.smart_parking_project.util;

import org.springframework.stereotype.Component;

import com.sahil.smart_parking_project.enums.VehicleType;
import com.sahil.smart_parking_project.service.ParkingRateService;

@Component
public class ParkingBookingSlotUtil {

	 private final ParkingRateService parkingRateService;

	    public ParkingBookingSlotUtil(ParkingRateService parkingRateService) {
	        this.parkingRateService = parkingRateService;
	    }
	
	public double getSurgeMultiplier(long occupied, long total) {

		if (total == 0) {
			return 1.0;
		}

		double occupancyPercent = (occupied * 100.0) / total;

		if (occupancyPercent >= 80) {
			return 1.5;
		}
		if (occupancyPercent >= 50) {
			return 1.2;
		}
		return 1.0;
	}

	 public double calculateAmount(
	            VehicleType type,
	            long hours,
	            double surgeMultiplier) {

	        double rate = parkingRateService.getRatePerHour(type);

	        return rate * hours * surgeMultiplier;
	    }
}
