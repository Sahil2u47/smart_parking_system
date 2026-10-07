package com.sahil.smart_parking_project.service;

import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.dto.ParkingBookingAnalyticsResponseDTO;
import com.sahil.smart_parking_project.enums.BookingStatus;
import com.sahil.smart_parking_project.repository.BookingRepository;

@Service
public class ParkingBookingAnalyticsService {

	private final BookingRepository bookingRepository;

	public ParkingBookingAnalyticsService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public ParkingBookingAnalyticsResponseDTO getBookingAnalytics() {

		long totalBookings = bookingRepository.count();

		long activeBookings = bookingRepository.countByStatus(BookingStatus.ACTIVE);

		long completedBookings = bookingRepository.countByStatus(BookingStatus.COMPLETED);


		Double revenue = bookingRepository.getTotalAmountByStatus(BookingStatus.COMPLETED);

		double totalRevenue = revenue != null ? revenue : 0.0;

		ParkingBookingAnalyticsResponseDTO response = new ParkingBookingAnalyticsResponseDTO();

		response.setTotalBookings(totalBookings);
		response.setActiveBookings(activeBookings);
		response.setCompletedBookings(completedBookings);
		response.setTotalRevenue(totalRevenue);

		return response;
	}

}
