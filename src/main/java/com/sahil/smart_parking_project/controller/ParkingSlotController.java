package com.sahil.smart_parking_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.ParkingSlotRequestDTO;
import com.sahil.smart_parking_project.dto.ParkingSlotResponseDTO;
import com.sahil.smart_parking_project.service.ParkingSlotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/parkingslot")
public class ParkingSlotController {

	private final ParkingSlotService parkingSlotService;

	public ParkingSlotController(ParkingSlotService parkingSlotService) {
		super();
		this.parkingSlotService = parkingSlotService;
	}

	@PostMapping("/register")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ParkingSlotResponseDTO> registerParkingSlot(@Valid @RequestBody ParkingSlotRequestDTO dto) {

		ParkingSlotResponseDTO response = parkingSlotService.registerParkingSlot(dto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
