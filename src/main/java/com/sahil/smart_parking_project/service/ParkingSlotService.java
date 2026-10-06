package com.sahil.smart_parking_project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.ParkingSlotRequestDTO;
import com.sahil.smart_parking_project.dto.ParkingSlotResponseDTO;
import com.sahil.smart_parking_project.entity.ParkingSlot;
import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.globalException.DuplicateResourceException;
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

	public List<ParkingSlotResponseDTO> getAllParkingSlots() {

		List<ParkingSlot> slots = parkingSlotRepository.findAll();

		return slots.stream().map(parkingSlotMapper::toParkingSlotResponseDTO).toList();
	}

	public ParkingSlotResponseDTO registerParkingSlot(ParkingSlotRequestDTO dto) {

		if (parkingSlotRepository.existsBySlotNumber(dto.getSlotNumber())) {
			throw new DuplicateResourceException("Parking slot already exists");
		}

		ParkingSlot slot = parkingSlotMapper.toParkingSlot(dto);
		slot.setStatus(SlotStatus.AVAILABLE);

		ParkingSlot savedSlot = parkingSlotRepository.saveAndFlush(slot);

		return parkingSlotMapper.toParkingSlotResponseDTO(savedSlot);
	}
}