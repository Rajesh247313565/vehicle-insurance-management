package com.insurence.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDto {
	
	@NotBlank(message = "Please Enter Email")
	@Email(message = "Enter valid mail")
	@NotNull(message = "Enter Email")
	private String email;
	
	@NotBlank(message = "password should not be blank")
	@NotNull(message = "Please enter password")
	private String password;

}
