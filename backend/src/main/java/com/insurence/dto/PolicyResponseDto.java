package com.insurence.dto;

import com.insurence.entity.VehicalType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicyResponseDto {
	
	private Long policyId;
	private String policyName;
	private Double policyAmount;
	private String description;
	private VehicalType vehicleType;
	

}
