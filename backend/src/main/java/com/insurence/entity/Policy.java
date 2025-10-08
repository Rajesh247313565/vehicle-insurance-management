package com.insurence.entity;

import java.time.LocalDate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POLICY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long policyId;

    private String policyName;
    private Double amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private VehicalType vehicalType;  

    private String customerName;

    private String securityName;   

    private LocalDate asOfDate;       
    private LocalDate settlementDate; 
    
    
    

    /**
     * Generate a security name from policy details.
     * Combines customer name, policy name, vehicle type, and asOfDate,
     * then appends a short hash for uniqueness.
     */
    public void generateSecurityName() {
        String base = (customerName == null ? "" : customerName.trim().replaceAll("\\s+", "")) +
                      "-" + (policyName == null ? "POL" : policyName.trim().replaceAll("\\s+", "")) +
                      "-" + (vehicalType == null ? "UNKNOWN" : vehicalType.name()) +
                      "-" + (asOfDate == null ? "NA" : asOfDate.toString());

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(base.getBytes());
            String hex = HexFormat.of().formatHex(digest).substring(0, 8);
            this.securityName = base + "-" + hex;
        } catch (NoSuchAlgorithmException e) {
            this.securityName = base + "-" + System.currentTimeMillis();
        }
    }

    /**
     * Validation rule:
     * A policy is valid if settlementDate < asOfDate.
     */
    public boolean isAsOfDateValidForIssuance() {
        if (this.asOfDate == null || this.settlementDate == null) {
            return false;
        }
        return this.settlementDate.isBefore(this.asOfDate);
    }
}
