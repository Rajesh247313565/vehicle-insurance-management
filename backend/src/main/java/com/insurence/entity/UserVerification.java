package com.insurence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SQLDelete(sql = "UPDATE VERIFICATION_REQUEST SET v_status='false' WHERE id=?")
@Where(clause = "v_status = true")
@Entity
@Table(name = "VERIFICATION_REQUEST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long policyId;
	
	private Long userId;
	
	
	@Enumerated(EnumType.STRING)
	private DocumentType docType;
	private String docNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private VerficationStatus status;
	
	private String regNumber;
	
	private Boolean vStatus;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime requestedDate;
	
	@Enumerated(EnumType.STRING)
	private InstallmentType installmentType;
}
