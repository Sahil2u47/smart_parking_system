package com.sahil.smart_parking_project.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.VehicleRequestDTO;
import com.sahil.smart_parking_project.dto.VehicleResponseDTO;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.entity.Vehicle;
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

	/**
	 * This method saves a vehicle to the database. It first retrieves the
	 * authenticated user's email from the security context, then fetches the
	 * corresponding User entity from the database. If the user is not found, it
	 * throws a RuntimeException. Next, it maps the VehicleRequestDTO to a Vehicle
	 * entity, sets the user for the vehicle, and saves it to the database. Finally,
	 * it maps the saved Vehicle entity to a VehicleResponseDTO and returns it.
	 * 
	 * @param vehicleRequestDTO
	 * @return
	 */
	public VehicleResponseDTO saveVehicleService(VehicleRequestDTO vehicleRequestDTO) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		System.out.println("saveVehicle Authentication: " + authentication);

		System.out.println("saveVehicle Email: " + email);

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("you are not authenticated please login and try again"));

		vehicleRepository.findByVehicleNumber(vehicleRequestDTO.getVehicleNumber()).ifPresent(v -> {
			throw new RuntimeException("Vehicle number already exists");
		});

		Vehicle vehicle = vehicleMapper.toVehicle(vehicleRequestDTO);

		vehicle.setUser(user);

		Vehicle savedVehicle = vehicleRepository.save(vehicle);

		return vehicleMapper.toVehicleResponseDTO(savedVehicle);
	}

}
