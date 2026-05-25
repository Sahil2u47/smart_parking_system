package com.sahil.smart_parking_project.map_struct;

import org.mapstruct.Mapper;

import com.sahil.smart_parking_project.dto.ParkingSlotRequestDTO;
import com.sahil.smart_parking_project.dto.ParkingSlotResponseDTO;
import com.sahil.smart_parking_project.entity.ParkingSlot;

@Mapper(componentModel = "spring")
public interface ParkingSlotMapper {
	
	 ParkingSlot toParkingSlot(ParkingSlotRequestDTO dto);

	    ParkingSlotResponseDTO toParkingSlotResponseDTO(ParkingSlot entity);

}
