package com.sahil.smart_parking_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.smart_parking_project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
