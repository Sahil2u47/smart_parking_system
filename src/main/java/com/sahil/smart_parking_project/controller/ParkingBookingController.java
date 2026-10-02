package com.sahil.smart_parking_project.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.BookingEntryDTO;
import com.sahil.smart_parking_project.dto.BookingResponseDTO;
import com.sahil.smart_parking_project.dto.ExitBookingDTO;
import com.sahil.smart_parking_project.dto.PageResponseDTO;
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
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingEntryDTO dto) {
		return ResponseEntity.ok(parkingSlotBookingService.createBooking(dto));
	}

	@PutMapping("/exit")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<BookingResponseDTO> exitBooking(@Valid @RequestBody ExitBookingDTO dto) {
		return ResponseEntity.ok(parkingSlotBookingService.exitBooking(dto));
	}

	@GetMapping("/my-bookings")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PageResponseDTO<BookingResponseDTO>> getMyBookings(Pageable pageable) {

		Page<BookingResponseDTO> page = parkingSlotBookingService.getMyBookings(pageable);

		PageResponseDTO<BookingResponseDTO> response = new PageResponseDTO<>(page.getContent(), page.getNumber(),
				page.getSize(), page.getTotalElements(), page.getTotalPages());

		return ResponseEntity.ok(response);
	}
}
