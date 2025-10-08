package com.insurence.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum InstallmentType {

	YEARLY(1.0, 1, 12),
	HALFYEARLY(0.02, 2, 6),
	QUARTERLY(0.02, 3, 3),
	MONTHLY(0.02, 12, 1);

	private Double intrestRate;
	private int installments;
	private int noOfMonths;

	InstallmentType(Double intrestRate, int installments, int noOfMonths) {
		this.intrestRate = intrestRate;
		this.installments = installments;
		this.noOfMonths = noOfMonths;
	}

	public Double getIntrestRate() {
		return intrestRate;
	}

	public int getInstallments() {
		return installments;
	}

	public int getNoOfMonths() {
		return noOfMonths;
	}

	@JsonCreator
	public static InstallmentType fromString(String key) {
		return key == null ? null : InstallmentType.valueOf(key.toUpperCase());
	}

}
