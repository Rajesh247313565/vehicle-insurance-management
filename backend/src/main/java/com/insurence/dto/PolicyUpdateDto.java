package com.insurence.dto;

import com.insurence.entity.VehicalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyUpdateDto {
	
	private Long pid;
	private String policyName;
	private Double policyAmount;
	private String description;
	private VehicalType vehichleType;
}
