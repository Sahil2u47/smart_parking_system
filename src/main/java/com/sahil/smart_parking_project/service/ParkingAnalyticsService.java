package com.sahil.smart_parking_project.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.ParkingAnalyticsResponseDTO;
import com.sahil.smart_parking_project.dto.ParkingVehicleTypeAnalyticsDTO;
import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.enums.VehicleType;
import com.sahil.smart_parking_project.repository.ParkingSlotRepository;

@Service
public class ParkingAnalyticsService {

	private final ParkingSlotRepository parkingSlotRepository;

	public ParkingAnalyticsService(ParkingSlotRepository parkingSlotRepository) {
		this.parkingSlotRepository = parkingSlotRepository;
	}

	public ParkingAnalyticsResponseDTO getParkingAnalytics() {

		// Overall parking analytics
		long totalSlots = parkingSlotRepository.count();

		long availableSlots = parkingSlotRepository.countByStatus(SlotStatus.AVAILABLE);

		long occupiedSlots = parkingSlotRepository.countByStatus(SlotStatus.OCCUPIED);

		long maintenanceSlots = parkingSlotRepository.countByStatus(SlotStatus.MAINTENANCE);

		double occupancyPercentage = 0.0;

		if (totalSlots > 0) {
			occupancyPercentage = (occupiedSlots * 100.0) / totalSlots;
		}

		ParkingAnalyticsResponseDTO response = new ParkingAnalyticsResponseDTO();

		response.setTotalSlots(totalSlots);
		response.setAvailableSlots(availableSlots);
		response.setOccupiedSlots(occupiedSlots);
		response.setMaintenanceSlots(maintenanceSlots);
		response.setOccupancyPercentage(occupancyPercentage);

		// Vehicle type wise analytics
		Map<String, ParkingVehicleTypeAnalyticsDTO> vehicleTypeAnalytics = new LinkedHashMap<>();

		for (VehicleType type : VehicleType.values()) {

			long total = parkingSlotRepository.countBySlotType(type);

			long available = parkingSlotRepository.countBySlotTypeAndStatus(type, SlotStatus.AVAILABLE);

			long occupied = parkingSlotRepository.countBySlotTypeAndStatus(type, SlotStatus.OCCUPIED);

			ParkingVehicleTypeAnalyticsDTO data = new ParkingVehicleTypeAnalyticsDTO();

			data.setTotal(total);
			data.setAvailable(available);
			data.setOccupied(occupied);

			vehicleTypeAnalytics.put(type.name(), data);
		}

		response.setVehicleTypeAnalytics(vehicleTypeAnalytics);

		return response;
	}
}