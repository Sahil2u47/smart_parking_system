package com.sahil.smart_parking_project.map_struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sahil.smart_parking_project.dto.BookingResponseDTO;
import com.sahil.smart_parking_project.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

	@Mapping(source = "id", target = "bookingId")
	@Mapping(source = "vehicle.vehicleNumber", target = "vehicleNumber")
	@Mapping(source = "slot.slotNumber", target = "slotNumber")
	@Mapping(target = "totalHours", ignore = true)
	BookingResponseDTO toBookingResponseDTO(Booking booking);

}
