package com.insurence.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



public class UserRegisterDto {
	
	@NotNull(message = "User Name should not be null")
	@NotBlank(message = "Please Enter User name")
	private String userName;
	
	@Email(message = "Enter valid email id")
	@NotBlank(message = "Please Enter Email id")
	private String email;
	
	@Size(min = 6, message = "password should be at least 6 char")
	@NotNull(message = "Please Enter Password")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
