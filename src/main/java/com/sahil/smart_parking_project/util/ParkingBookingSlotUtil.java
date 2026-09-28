package com.sahil.smart_parking_project.util;

import org.springframework.stereotype.Component;

import com.sahil.smart_parking_project.enums.VehicleType;

@Component
public class ParkingBookingSlotUtil {

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

	public double calculateAmount(VehicleType type, long hours, double surgeMultiplier) {

		double rate = 0;

		switch (type) {

		case BIKE:
			rate = 20;
			break;

		case CAR:
			rate = 50;
			break;

		case AUTO:
			rate = 40;
			break;
		}

		return rate * hours * surgeMultiplier;
	}

}
