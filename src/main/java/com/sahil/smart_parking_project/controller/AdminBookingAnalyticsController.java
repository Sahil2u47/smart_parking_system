package com.sahil.smart_parking_project.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.ParkingBookingAnalyticsResponseDTO;
import com.sahil.smart_parking_project.service.ParkingBookingAnalyticsService;

@RestController
@RequestMapping("/admin/parking")
public class AdminBookingAnalyticsController {

	private final ParkingBookingAnalyticsService parkingBookingAnalyticsService;

	public AdminBookingAnalyticsController(ParkingBookingAnalyticsService parkingBookingAnalyticsService) {
		this.parkingBookingAnalyticsService = parkingBookingAnalyticsService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/booking-analytics")
	public ParkingBookingAnalyticsResponseDTO getBookingAnalytics() {

		return parkingBookingAnalyticsService.getBookingAnalytics();
	}

}
