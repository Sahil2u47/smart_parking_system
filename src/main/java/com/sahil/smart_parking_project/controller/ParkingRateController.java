package com.sahil.smart_parking_project.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.smart_parking_project.dto.ParkingRateRequestDTO;
import com.sahil.smart_parking_project.dto.ParkingRateResponseDTO;
import com.sahil.smart_parking_project.entity.ParkingRate;
import com.sahil.smart_parking_project.enums.VehicleType;
import com.sahil.smart_parking_project.map_struct.ParkingRateMapper;
import com.sahil.smart_parking_project.service.ParkingRateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/parking-rates")
public class ParkingRateController {

	  private final ParkingRateService parkingRateService;
	    private final ParkingRateMapper parkingRateMapper;

	    public ParkingRateController(
	            ParkingRateService parkingRateService,
	            ParkingRateMapper parkingRateMapper) {

	        this.parkingRateService = parkingRateService;
	        this.parkingRateMapper = parkingRateMapper;
	    }


	    @GetMapping
	    public List<ParkingRateResponseDTO> getAllRates() {

	        return parkingRateService
	                .getAllRates()
	                .stream()
	                .map(parkingRateMapper::toResponseDTO)
	                .toList();
	    }
	    
	    
	    @PreAuthorize("hasRole('ADMIN')")
	    @PutMapping("/{vehicleType}")
	    public ParkingRateResponseDTO updateRate(
	            @PathVariable VehicleType vehicleType,
	            @Valid @RequestBody ParkingRateRequestDTO request) {

	        ParkingRate parkingRate =
	                parkingRateService.updateRate(
	                        vehicleType,
	                        request.getRatePerHour()
	                );

	        return parkingRateMapper.toResponseDTO(parkingRate);
	    }

}
