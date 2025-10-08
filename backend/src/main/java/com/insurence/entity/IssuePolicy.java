package com.insurence.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ISSUE_POLICY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuePolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long issueId;

	private Long userId;
	private Long policyId;

	@Enumerated(EnumType.STRING)
	private VehicalType vehichalType;
	
	private boolean paymentInitialized;

	private Double amountPaid = 0.0;
	private Double remainingAmount;
	private Integer installmentsLeft=0;

	@Enumerated(EnumType.STRING)
	private InstallmentType installmentType;

	private String registrationNo;

	@Enumerated(EnumType.STRING)
	private DocumentType documentType;
	
	private String documentNumber;

	private LocalDate issuedAt;
	private LocalDate nextDueAt;
	private LocalDate policyEndDate;
	
	private PolicyStatus policyStatus;
	
    @OneToOne(mappedBy = "policy", cascade = CascadeType.ALL)
    @JsonIgnore
	private PaymentDetails paymentDetails;

	
    public void setIssuedAt(LocalDate issuedAt) {
        this.issuedAt = issuedAt;

        if (issuedAt != null && installmentType != null) {
            this.nextDueAt = issuedAt.plusMonths(installmentType.getNoOfMonths());
            this.policyEndDate = issuedAt.plusYears(1).minusDays(1);
        }
    }

}
