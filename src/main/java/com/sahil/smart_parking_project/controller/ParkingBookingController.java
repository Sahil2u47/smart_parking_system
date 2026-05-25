package com.sahil.smart_parking_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.BookingEntryDTO;
import com.sahil.smart_parking_project.dto.ExitBookingDTO;
import com.sahil.smart_parking_project.entity.Booking;
import com.sahil.smart_parking_project.service.ParkingSlotBookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/booking")
public class ParkingBookingController {

	private final ParkingSlotBookingService parkingSlotBookingService;

	public ParkingBookingController(ParkingSlotBookingService parkingSlotBookingService) {
		super();
		this.parkingSlotBookingService = parkingSlotBookingService;
	}

	@PostMapping("/book")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingEntryDTO dto) {

		return ResponseEntity.ok(parkingSlotBookingService.createBooking(dto));
	}

	@PutMapping("/exit")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Booking> exitBooking(@Valid @RequestBody ExitBookingDTO dto) {

		return ResponseEntity.ok(parkingSlotBookingService.exitBooking(dto));
	}

}
