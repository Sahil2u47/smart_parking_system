package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.smart_parking_project.entity.Role;
import com.sahil.smart_parking_project.enums.RoleType;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleType name);
	
}
