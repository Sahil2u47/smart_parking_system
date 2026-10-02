package com.sahil.smart_parking_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.BookingResponseDTO;
import com.sahil.smart_parking_project.dto.CancelBookingDTO;
import com.sahil.smart_parking_project.service.ParkingSlotBookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class ParkingController {

	private final ParkingSlotBookingService parkingSlotBookingService;

	public ParkingController(ParkingSlotBookingService parkingSlotBookingService) {
		this.parkingSlotBookingService = parkingSlotBookingService;
	}

	@PutMapping("/cancel")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<BookingResponseDTO> cancelBooking(@Valid @RequestBody CancelBookingDTO dto) {

		return ResponseEntity.ok(parkingSlotBookingService.cancelBooking(dto));
	}
}