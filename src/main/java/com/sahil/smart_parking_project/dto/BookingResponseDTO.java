package com.sahil.smart_parking_project.dto;

import java.time.LocalDateTime;

import com.sahil.smart_parking_project.enums.BookingStatus;

public class BookingResponseDTO {

	private Long bookingId;

	private String vehicleNumber;

	private String slotNumber;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Long totalHours;

	private Double totalAmount;

	private BookingStatus status;

	public BookingResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookingResponseDTO(Long bookingId, String vehicleNumber, String slotNumber, LocalDateTime startTime,
			LocalDateTime endTime, Long totalHours, Double totalAmount, BookingStatus status) {
		super();
		this.bookingId = bookingId;
		this.vehicleNumber = vehicleNumber;
		this.slotNumber = slotNumber;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalHours = totalHours;
		this.totalAmount = totalAmount;
		this.status = status;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Long totalHours) {
		this.totalHours = totalHours;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

}
