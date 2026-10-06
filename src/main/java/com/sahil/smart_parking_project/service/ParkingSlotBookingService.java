package com.sahil.smart_parking_project.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.BookingEntryDTO;
import com.sahil.smart_parking_project.dto.BookingResponseDTO;
import com.sahil.smart_parking_project.dto.CancelBookingDTO;
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

import jakarta.transaction.Transactional;

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

	@Transactional
	public BookingResponseDTO createBooking(BookingEntryDTO dto) {

		// Logged-in user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not logged in please login and try again"));

		// Automatically find an available slot for the requested vehicle type
		ParkingSlot slot = slotRepository.findFirstByStatusAndSlotType(SlotStatus.AVAILABLE, dto.getVehicleType())
				.orElseThrow(() -> new SlotUnavailableException("No available slot for " + dto.getVehicleType()));

		System.out.println("Slot allocated: " + slot.getSlotNumber() + " for vehicle type: " + dto.getVehicleType());

		// Demand-based surge pricing — calculated at booking time and locked on the
		// booking
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
		} else {
//			Ownership check
			if (!vehicle.getUser().getId().equals(user.getId())) {
				throw new UnauthorizedActionException("This vehicle belongs to another user");
			}
		}

		// Active Booking check

		Booking activeBooking = bookingRepository
				.findByVehicleVehicleNumberAndStatus(dto.getVehicleNumber(), BookingStatus.ACTIVE).orElse(null);

		if (activeBooking != null) {
			throw new SlotUnavailableException("Vehicle already has an active booking");
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

	@Transactional
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

		// Calculate duration (partial hour is charged as a full hour)
		long minutes = Duration.between(booking.getStartTime(), endTime).toMinutes();
		long hours = (minutes + 59) / 60;

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
		responseDTO.setTotalAmount(totalAmount);

		return responseDTO;
	}
	
	
	public Page<BookingResponseDTO> getMyBookings(Pageable pageable) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		Page<Booking> bookings = bookingRepository.findByUserEmail(email, pageable);

		return bookings.map(bookingMapper::toBookingResponseDTO);
	}

//	Cancel booking

	@Transactional
	public BookingResponseDTO cancelBooking(CancelBookingDTO dto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		Booking booking = bookingRepository
				.findByVehicleVehicleNumberAndStatus(dto.getVehicleNumber(), BookingStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Active booking not found for vehicle: " + dto.getVehicleNumber()));

		// Ownership check
		if (!booking.getUser().getEmail().equals(email)) {
			throw new UnauthorizedActionException("You are not authorized to cancel this booking");
		}

		// Cancel booking
		booking.setStatus(BookingStatus.CANCELLED);

		// Release parking slot
		ParkingSlot slot = booking.getSlot();
		slot.setStatus(SlotStatus.AVAILABLE);

		bookingRepository.save(booking);
		slotRepository.save(slot);

		return bookingMapper.toBookingResponseDTO(booking);
	}

}