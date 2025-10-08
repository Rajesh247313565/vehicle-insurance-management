package com.insurence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
	
	private Long userId;
	private String userName;
	private String email;
	private Integer activePolicies;

}
