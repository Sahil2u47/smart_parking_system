package com.sahil.smart_parking_project.map_struct;

import org.mapstruct.Mapper;

import com.sahil.smart_parking_project.dto.ParkingRateResponseDTO;
import com.sahil.smart_parking_project.entity.ParkingRate;

@Mapper(componentModel = "spring")
public interface ParkingRateMapper {

	ParkingRateResponseDTO toResponseDTO(ParkingRate parkingRate);
	
}
