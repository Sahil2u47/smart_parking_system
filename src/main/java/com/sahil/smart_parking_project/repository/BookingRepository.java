package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sahil.smart_parking_project.entity.Booking;
import com.sahil.smart_parking_project.enums.BookingStatus;

import jakarta.persistence.LockModeType;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Booking> findByVehicleVehicleNumberAndStatus(String vehicleNumber, BookingStatus status);

	long countByStatus(BookingStatus status);

	@Query("""
			    SELECT COALESCE(SUM(b.amount), 0)
			    FROM Booking b
			    WHERE b.status = :status
			""")
	Double getTotalAmountByStatus(@Param("status") BookingStatus status);

	Page<Booking> findByUserEmail(String email, Pageable pageable);

}
