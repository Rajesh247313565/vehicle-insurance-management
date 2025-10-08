package com.insurence.dto;

import com.insurence.entity.InstallmentType;

import lombok.Data;

@Data
public class AmountRequest {
	private Long policyId;
	private InstallmentType installmentType;
}
