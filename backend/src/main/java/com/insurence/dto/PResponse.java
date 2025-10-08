package com.insurence.dto;

import com.insurence.entity.InstallmentType;
import com.insurence.entity.VehicalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PResponse {

	private Long pid;
	private String username;
	private VehicalType vehicleType;
	private InstallmentType installmentType;
	private Integer installmentsLeft;
	
	
		
}
