package com.insurence.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "PAYMENT_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Double amountPaid;
	private Double firstPayment;
	private Long noOfPayMents=0L;
	private String paymentMethod;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate issuedAt;
	
	@OneToOne
	@JoinColumn(name = "policy_id", referencedColumnName = "issueId")
	private IssuePolicy policy;
}
