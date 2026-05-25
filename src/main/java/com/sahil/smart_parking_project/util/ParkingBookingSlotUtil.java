package com.sahil.smart_parking_project.util;

import com.sahil.smart_parking_project.enums.VehicleType;

public class ParkingBookingSlotUtil {

	public double calculateAmount(VehicleType type, long hours) {

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

		return rate * hours;
	}

}
