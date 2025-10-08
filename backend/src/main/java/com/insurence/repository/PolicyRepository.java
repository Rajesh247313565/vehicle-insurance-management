package com.insurence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurence.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
	
	Optional<Policy> findByPolicyName(String name);
	

}
