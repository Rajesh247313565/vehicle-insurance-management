package com.insurence.dto;

import java.time.LocalDateTime;

import com.insurence.entity.DocumentType;
import com.insurence.entity.VehicalType;
import com.insurence.entity.VerficationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminVerficationResponseDto {
	
	private Long id;
	private Long policyId;
	private String policyName;
	private Double annualAmount;
	
	private Long userId;
	private String userName;
	private String email;
	private Integer activePolicies;
	
	private VehicalType vehicleType;
	private String regNumber;
	
	private DocumentType docType;
	private String docNumber;
	
	
	private VerficationStatus status;
	private LocalDateTime requestedDate;

}
