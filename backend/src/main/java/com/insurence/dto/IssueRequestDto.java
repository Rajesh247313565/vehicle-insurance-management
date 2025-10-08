package com.insurence.dto;

import com.insurence.entity.DocumentType;
import com.insurence.entity.InstallmentType;
import com.insurence.entity.PolicyStatus;
import com.insurence.entity.VehicalType;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequestDto {

	@NotBlank(message = "User id should not be blank")
	@NotNull(message = "Enter User id")
    private Long userId;
	
	@NotBlank(message = "Policy id should not be blank")
	@NotNull(message = "Enter Policy id")
    private Long policyId;

	@NotNull(message = "Select Vehicle type")
    private VehicalType vehicalType;

	@NotNull(message = "Enter RegNo")
	@NotBlank(message = "RegNo shuold not be blank")
    private String regNo;

	@NotNull(message = "Select Document type")
    private DocumentType documentType;
	
	@NotNull(message = "Enter DocNum")
	@NotBlank(message = "DocNum shuold not be blank")
    private String documentNumber;

	@NotNull(message = "Select Installment type")
    private InstallmentType installmentType;
	
	private PolicyStatus policyStatus;

    private Double amountPaid;
    
    public Double getAmountPaid() {
        return amountPaid == null ? 0.0 : amountPaid;
    }

    
}

