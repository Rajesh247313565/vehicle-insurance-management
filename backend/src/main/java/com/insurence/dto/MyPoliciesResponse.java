package com.insurence.dto;

import java.time.LocalDate;

import com.insurence.entity.DocumentType;
import com.insurence.entity.InstallmentType;
import com.insurence.entity.PolicyStatus;
import com.insurence.entity.VehicalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPoliciesResponse {

	private Long userId;
	
	private Long policyId;
	private String policyName;
	
	private VehicalType vehicalType;
	private String registrationNo;
	private DocumentType documentType;
	private String documentNumber;
	
	private LocalDate issuedAt;
	private LocalDate nextDueAt;
	
	private InstallmentType installmentType;
	private Integer installmentsLeft;
	
	private Double amountPaid;
	private Double remainingAmount;
	private LocalDate endDate;
	private Double policyAmount;
	private Double installmentAmount;
	
	private PolicyStatus policyStatus;
	
	private boolean isPaymentInitialized;
	
	
	
	
}
