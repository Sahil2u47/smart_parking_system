package com.sahil.smart_parking_project.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.ParkingAnalyticsResponseDTO;
import com.sahil.smart_parking_project.service.ParkingAnalyticsService;

@RestController
@RequestMapping("/admin/parking")
public class AdminAnalyticsController {

	 private final ParkingAnalyticsService parkingAnalyticsService;

	    public AdminAnalyticsController(
	            ParkingAnalyticsService parkingAnalyticsService) {
	        this.parkingAnalyticsService = parkingAnalyticsService;
	    }
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/analytics")
	    public ParkingAnalyticsResponseDTO getParkingAnalytics() {

	        return parkingAnalyticsService.getParkingAnalytics();
	    }
	
}
