package com.insurence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurence.entity.UserVerification;

public interface VerificationRepository extends JpaRepository<UserVerification, Long> {

}
