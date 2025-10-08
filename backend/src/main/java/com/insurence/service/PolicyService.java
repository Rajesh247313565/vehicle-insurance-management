package com.insurence.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insurence.dto.IssueRequestDto;
import com.insurence.dto.IssueResponseDto;
import com.insurence.dto.PolicyRegisterDto;
import com.insurence.dto.PolicyResponseDto;
import com.insurence.entity.IssuePolicy;
import com.insurence.entity.Policy;
import com.insurence.error.NoPoliciesFoundException;
import com.insurence.error.PolicyAlreadyExistsException;
import com.insurence.repository.IssuPolicyRepository;
import com.insurence.repository.PolicyRepository;

@Service
public class PolicyService {
	
	@Autowired
	private IssuPolicyRepository iRepo;
	@Autowired
	private PolicyRepository pRepo;



	public IssueResponseDto issue(IssueRequestDto request) {
		
		
	    Optional<IssuePolicy> existingIssue = iRepo
	            .findByUserIdAndPolicyIdAndVehichalType(
	                request.getUserId(),
	                request.getPolicyId(),
	                request.getVehicalType()
	            );

	    if (existingIssue.isPresent()) {
	        throw new PolicyAlreadyExistsException("Policy is already taken by the user.");
	    }

	    
	    Policy policy = pRepo.findById(request.getPolicyId())
	            .orElseThrow(() -> new NoPoliciesFoundException("Policy not found"));


	    IssuePolicy newPolicy = new IssuePolicy();
	    newPolicy.setPolicyId(request.getPolicyId());
	    newPolicy.setUserId(request.getUserId());
	    newPolicy.setVehichalType(request.getVehicalType());
	    newPolicy.setInstallmentType(request.getInstallmentType());
	    
	    newPolicy.setDocumentNumber(request.getDocumentNumber());
	    newPolicy.setDocumentType(request.getDocumentType());
	    newPolicy.setRegistrationNo(request.getRegNo());
	    newPolicy.setPolicyEndDate(newPolicy.getPolicyEndDate());
	    
	    newPolicy.setPaymentInitialized(false);

	    System.out.println("policy service");
	    System.out.println(newPolicy);
	    
	    iRepo.save(newPolicy);

	    return IssueResponseDto.builder()
	            .issueId(newPolicy.getIssueId())
	            .policyId(newPolicy.getPolicyId())
	            .userId(newPolicy.getUserId())
	            .vehicalType(newPolicy.getVehichalType())
	            .build();
	}


	public List<Policy> getAllPolicies() {
		List<Policy> policyList = pRepo.findAll();
		if(policyList.isEmpty()) {
			throw new NoPoliciesFoundException("No policies found");
		}
		return policyList;
	}

	


}
