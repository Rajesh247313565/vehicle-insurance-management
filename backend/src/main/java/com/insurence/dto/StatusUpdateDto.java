package com.insurence.dto;

import com.insurence.entity.VerficationStatus;

import lombok.Data;

@Data
public class StatusUpdateDto {
	private Long userId;
	private Long verificationId;
	private String remarks;
	private VerficationStatus status;
}
