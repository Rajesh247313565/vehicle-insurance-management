package com.insurence.dto;

import com.insurence.entity.DocumentType;
import com.insurence.entity.InstallmentType;
import com.insurence.entity.VehicalType;
import com.insurence.entity.VerficationStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerficationDto {
	
	private Long userId;
	private Long policyId;
	private String regNumber;
	private VehicalType vehichleType;
	private DocumentType docType;
	private String docNumber;
	private VerficationStatus status;
	private InstallmentType installmentType;

}
