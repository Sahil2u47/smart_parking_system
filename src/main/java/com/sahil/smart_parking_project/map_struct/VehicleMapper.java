package com.sahil.smart_parking_project.map_struct;

import org.mapstruct.Mapping;
import com.sahil.smart_parking_project.dto.VehicleRequestDTO;
import com.sahil.smart_parking_project.dto.VehicleResponseDTO;
import com.sahil.smart_parking_project.entity.Vehicle;

public interface VehicleMapper {
	
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Vehicle toVehicle(VehicleRequestDTO dto);
	
	@Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.email", target = "userEmail")
    VehicleResponseDTO toVehicleResponseDTO(Vehicle vehicle);

}
