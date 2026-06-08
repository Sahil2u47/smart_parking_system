package com.sahil.smart_parking_project.map_struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.sahil.smart_parking_project.dto.UserRequestDTO;
import com.sahil.smart_parking_project.dto.UserResponseDTO;
import com.sahil.smart_parking_project.entity.Role;
import com.sahil.smart_parking_project.entity.User;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "roleId", target = "role")
    User toUser(UserRequestDTO dto);

    default Role map(Long roleId) {
        if (roleId == null) return null;

        Role role = new Role();
        role.setId(roleId);
        return role;
    }

    @Mapping(source = "role", target = "role")
    UserResponseDTO toUserResponseDTO(User user);

    default String map(Role role) {
        return (role != null && role.getName() != null)
                ? role.getName().name()
                : null;
    }
}