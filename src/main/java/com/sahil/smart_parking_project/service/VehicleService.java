package com.sahil.smart_parking_project.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.VehicleRequestDTO;
import com.sahil.smart_parking_project.dto.VehicleResponseDTO;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.entity.Vehicle;
import com.sahil.smart_parking_project.globalException.DuplicateResourceException;
import com.sahil.smart_parking_project.globalException.ResourceNotFoundException;
import com.sahil.smart_parking_project.map_struct.VehicleMapper;
import com.sahil.smart_parking_project.repository.UserRepository;
import com.sahil.smart_parking_project.repository.VehicleRepository;

@Service
public class VehicleService {

	private final VehicleRepository vehicleRepository;
	private final UserRepository userRepository;
	private final VehicleMapper vehicleMapper;

	public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository,
			VehicleMapper vehicleMapper) {
		super();
		this.vehicleRepository = vehicleRepository;
		this.userRepository = userRepository;
		this.vehicleMapper = vehicleMapper;
	}

	public boolean isVehicleNumberExists(String vehicleNumber) {
		return vehicleRepository.findByVehicleNumber(vehicleNumber).isPresent();
	}

	public VehicleResponseDTO saveVehicleService(VehicleRequestDTO vehicleRequestDTO) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		System.out.println("saveVehicle Authentication: " + authentication);
		System.out.println("saveVehicle Email: " + email);

		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("You are not authenticated, please login and try again"));

		vehicleRepository.findByVehicleNumber(vehicleRequestDTO.getVehicleNumber()).ifPresent(v -> {
			throw new DuplicateResourceException("Vehicle number already exists");
		});

		Vehicle vehicle = vehicleMapper.toVehicle(vehicleRequestDTO);
		vehicle.setUser(user);

		Vehicle savedVehicle = vehicleRepository.save(vehicle);

		return vehicleMapper.toVehicleResponseDTO(savedVehicle);
	}
	
//	find user vehicle
	
	public List<VehicleResponseDTO> getMyVehiclesService() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "You are not authenticated, please login and try again"));

	    List<Vehicle> vehicles = vehicleRepository.findByUser(user);

	    return vehicles.stream()
	            .map(vehicleMapper::toVehicleResponseDTO)
	            .toList();
	}
	
	
}