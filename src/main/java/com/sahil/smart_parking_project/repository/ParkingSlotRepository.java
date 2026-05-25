package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.smart_parking_project.entity.ParkingSlot;
import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.enums.VehicleType;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

	Optional<ParkingSlot> findBySlotNumber(String slotNumber);

	boolean existsBySlotNumber(String slotNumber);

	Optional<ParkingSlot> findFirstByStatusAndSlotType(SlotStatus status, VehicleType slotType);

}
