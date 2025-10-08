package com.insurence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurence.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

}
