package com.insurence.dto;

import java.time.LocalDateTime;

import com.insurence.entity.PolicyStatus;
import com.insurence.entity.VehicalType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueResponseDto {
	
	private Long issueId;
	private Long userId;
	private Long policyId;
	private VehicalType vehicalType;
	
	private Double totalAmount;
	private Double amountPaid; 
	private Double remainingAmount;
	private Integer installmentsLeft;
	private LocalDateTime endOfInstallment;
	
	
}
