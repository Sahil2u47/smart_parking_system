package com.sahil.smart_parking_project.service;

import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.ParkingSlotRequestDTO;
import com.sahil.smart_parking_project.dto.ParkingSlotResponseDTO;
import com.sahil.smart_parking_project.entity.ParkingSlot;
import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.map_struct.ParkingSlotMapper;
import com.sahil.smart_parking_project.repository.ParkingSlotRepository;

@Service
public class ParkingSlotService {

	private final ParkingSlotRepository parkingSlotRepository;

	private final ParkingSlotMapper parkingSlotMapper;

	public ParkingSlotService(ParkingSlotRepository parkingSlotRepository, ParkingSlotMapper parkingSlotMapper) {
		super();
		this.parkingSlotRepository = parkingSlotRepository;
		this.parkingSlotMapper = parkingSlotMapper;
	}

	public ParkingSlotResponseDTO registerParkingSlot(ParkingSlotRequestDTO dto) {

		if (parkingSlotRepository.existsBySlotNumber(dto.getSlotNumber())) {
			throw new RuntimeException("Parking slot already exists");
		}

		ParkingSlot slot = parkingSlotMapper.toParkingSlot(dto);

		// Default status
		slot.setStatus(SlotStatus.AVAILABLE);

		ParkingSlot savedSlot = parkingSlotRepository.saveAndFlush(slot);

		//
		ParkingSlotResponseDTO response = parkingSlotMapper.toParkingSlotResponseDTO(savedSlot);

		return response;
	}

}
