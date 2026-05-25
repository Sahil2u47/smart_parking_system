package com.sahil.smart_parking_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.VehicleRequestDTO;
import com.sahil.smart_parking_project.dto.VehicleResponseDTO;
import com.sahil.smart_parking_project.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

	private final VehicleService vehicleService;

	public VehicleController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}

	@PostMapping(value = "/saveVehicle")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<VehicleResponseDTO> saveVehicleController(
			@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {

		VehicleResponseDTO response = vehicleService.saveVehicleService(vehicleRequestDTO);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
