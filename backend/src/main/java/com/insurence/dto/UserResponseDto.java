package com.insurence.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

	private Long userId;
	private String userName;
	private String email;
	
	private String token;
	
}
