package com.insurence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurence.entity.IssuePolicy;
import com.insurence.entity.VehicalType;

public interface IssuPolicyRepository extends JpaRepository<IssuePolicy, Long> {

	Optional<IssuePolicy> findByUserIdAndPolicyIdAndVehichalType(Long userId, Long ploicyId, VehicalType vehichalType);

	List<IssuePolicy> findByUserId(Long userId);
	
	Optional<IssuePolicy> findByUserIdAndPolicyId(Long userId, Long policyId);
	
}
