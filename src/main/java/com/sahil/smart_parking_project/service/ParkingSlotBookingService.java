package com.sahil.smart_parking_project.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.BookingEntryDTO;
import com.sahil.smart_parking_project.dto.BookingResponseDTO;
import com.sahil.smart_parking_project.dto.ExitBookingDTO;
import com.sahil.smart_parking_project.entity.Booking;
import com.sahil.smart_parking_project.entity.ParkingSlot;
import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.entity.Vehicle;
import com.sahil.smart_parking_project.enums.BookingStatus;
import com.sahil.smart_parking_project.enums.SlotStatus;
import com.sahil.smart_parking_project.enums.VehicleType;
import com.sahil.smart_parking_project.globalException.ResourceNotFoundException;
import com.sahil.smart_parking_project.globalException.SlotUnavailableException;
import com.sahil.smart_parking_project.globalException.UnauthorizedActionException;
import com.sahil.smart_parking_project.map_struct.BookingMapper;
import com.sahil.smart_parking_project.repository.BookingRepository;
import com.sahil.smart_parking_project.repository.ParkingSlotRepository;
import com.sahil.smart_parking_project.repository.UserRepository;
import com.sahil.smart_parking_project.repository.VehicleRepository;
import com.sahil.smart_parking_project.util.ParkingBookingSlotUtil;

@Service
public class ParkingSlotBookingService {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final VehicleRepository vehicleRepository;
	private final ParkingSlotRepository slotRepository;
	private final ParkingBookingSlotUtil parkingBookingSlotUtil;
	private final BookingMapper bookingMapper;

	public ParkingSlotBookingService(BookingRepository bookingRepository, UserRepository userRepository,
			VehicleRepository vehicleRepository, ParkingSlotRepository slotRepository,
			ParkingBookingSlotUtil parkingBookingSlotUtil, BookingMapper bookingMapper) {
		super();
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
		this.vehicleRepository = vehicleRepository;
		this.slotRepository = slotRepository;
		this.parkingBookingSlotUtil = parkingBookingSlotUtil;
		this.bookingMapper = bookingMapper;
	}

	public BookingResponseDTO createBooking(BookingEntryDTO dto) {

		// Logged-in user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not logged in please login and try again"));

		// Find parking slot
		ParkingSlot slot = slotRepository.findBySlotNumber(dto.getSlotNumber())
				.orElseThrow(() -> new ResourceNotFoundException("Slot is not Found"));

		System.out.println("Slot found: " + slot.getSlotNumber() + " with status: " + slot.getStatus());

		// Check slot availability
		if (slot.getStatus() == SlotStatus.OCCUPIED) {
			System.out.println("Slot is already occupied: " + slot.getSlotNumber());
			throw new SlotUnavailableException("Slot already occupied");
		}

		// Demand-based surge pricing — calculated at booking time and locked on the booking
		long totalSlotsOfType = slotRepository.countBySlotType(dto.getVehicleType());
		long occupiedSlotsOfType = slotRepository.countBySlotTypeAndStatus(dto.getVehicleType(), SlotStatus.OCCUPIED);
		double surgeMultiplier = parkingBookingSlotUtil.getSurgeMultiplier(occupiedSlotsOfType, totalSlotsOfType);

		// Find vehicle
		Vehicle vehicle = vehicleRepository.findByVehicleNumber(dto.getVehicleNumber()).orElse(null);

		// Create vehicle if not exists
		if (vehicle == null) {

			vehicle = new Vehicle();

			vehicle.setVehicleNumber(dto.getVehicleNumber());
			vehicle.setVehicleType(dto.getVehicleType());
			vehicle.setBrand(dto.getBrand());
			vehicle.setColor(dto.getColor());

			vehicle.setUser(user);

			vehicle = vehicleRepository.save(vehicle);
		}

		// Create booking
		Booking booking = new Booking();

		booking.setUser(user);
		booking.setVehicle(vehicle);
		booking.setSlot(slot);
		booking.setStatus(BookingStatus.ACTIVE);
		booking.setSurgeMultiplier(surgeMultiplier);

		// Mark slot occupied
		slot.setStatus(SlotStatus.OCCUPIED);
		slotRepository.save(slot);

		Booking saved = bookingRepository.save(booking);

		return bookingMapper.toBookingResponseDTO(saved);
	}

	/**
	 * Exit booking and calculate amount
	 * 
	 * @param dto
	 * @return
	 */
	public BookingResponseDTO exitBooking(ExitBookingDTO dto) {

		// Logged-in user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not logged in please login and try again"));

		// Find active booking
		Booking booking = bookingRepository
				.findByVehicleVehicleNumberAndStatus(dto.getVehicleNumber(), BookingStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Active booking not found"));

		// Security check
		if (!booking.getUser().getId().equals(user.getId())) {
			throw new UnauthorizedActionException("You cannot exit another user's vehicle");
		}

		// Exit time
		LocalDateTime endTime = LocalDateTime.now();

		booking.setEndTime(endTime);

		// Calculate duration
		long hours = Duration.between(booking.getStartTime(), endTime).toHours();

		// Minimum 1 hour
		if (hours == 0) {
			hours = 1;
		}

		// Rate calculation — uses the surge multiplier locked in at booking time
		VehicleType vehicleType = booking.getVehicle().getVehicleType();

		double surgeMultiplier = booking.getSurgeMultiplier() != null ? booking.getSurgeMultiplier() : 1.0;

		double totalAmount = parkingBookingSlotUtil.calculateAmount(vehicleType, hours, surgeMultiplier);

		booking.setAmount(totalAmount);

		// Complete booking
		booking.setStatus(BookingStatus.COMPLETED);

		// Make slot available
		ParkingSlot slot = booking.getSlot();

		slot.setStatus(SlotStatus.AVAILABLE);

		slotRepository.save(slot);

		Booking saved = bookingRepository.save(booking);

		BookingResponseDTO responseDTO = bookingMapper.toBookingResponseDTO(saved);
		responseDTO.setTotalHours(hours);

		return responseDTO;
	}

}