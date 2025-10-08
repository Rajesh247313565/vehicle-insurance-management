package com.insurence.dto;

import com.insurence.entity.VehicalType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyRegisterDto {
	
	private String policyName;
	private Double policyAmount;
	private String description;
	private VehicalType vehichleType;

}
