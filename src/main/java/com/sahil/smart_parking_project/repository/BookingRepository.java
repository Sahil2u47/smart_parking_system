package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.smart_parking_project.entity.Booking;
import com.sahil.smart_parking_project.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	Optional<Booking> findByVehicleVehicleNumberAndStatus(String vehicleNumber, BookingStatus status);

}
