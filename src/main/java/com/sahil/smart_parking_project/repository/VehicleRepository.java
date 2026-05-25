package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.smart_parking_project.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	Optional<Vehicle> findByVehicleNumber(String vehicleNumber);

}
