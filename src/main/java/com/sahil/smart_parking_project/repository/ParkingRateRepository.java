package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.smart_parking_project.entity.ParkingRate;
import com.sahil.smart_parking_project.enums.VehicleType;

@Repository
public interface ParkingRateRepository extends JpaRepository<ParkingRate, Long> {

  Optional<ParkingRate> findByVehicleTypeAndActiveTrue(VehicleType vehicleType);
	
}
