package com.insurence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.insurence.dto.AdminVerficationResponseDto;
import com.insurence.dto.PResponse;
import com.insurence.dto.PolicyRegisterDto;
import com.insurence.dto.PolicyResponseDto;
import com.insurence.dto.PolicyUpdateDto;
import com.insurence.dto.StatusUpdateDto;
import com.insurence.dto.UserInfoDto;
import com.insurence.dto.UserRegisterDto;
import com.insurence.dto.UserResponseDto;
import com.insurence.entity.User;
import com.insurence.service.AdminService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@PostMapping("/registerUser")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegisterDto register){
		return ResponseEntity.ok(service.registerUser(register));
	}

	@PostMapping("/registerAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDto> registerAdmin(@RequestBody UserRegisterDto request){
		return ResponseEntity.ok(service.registerAdmin(request));
	}
	
	@GetMapping("/getAllUsers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(service.getAllUsers());
	}
	
	@PostMapping("/createpolicy")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PolicyResponseDto> createPolicy(@RequestBody PolicyRegisterDto request) {
		return ResponseEntity.ok(service.createPolicy(request));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getIssuedPolicyInfo")
	public ResponseEntity<List<PResponse>> getAllIssuedPolicies(){
		return ResponseEntity.ok(service.getAllIssuedPolicies());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/updatePolicy")
	public ResponseEntity<PolicyResponseDto> editPolicy(@RequestBody PolicyUpdateDto request){
		return ResponseEntity.ok(service.updatePolicy(request));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/removePolicy/{pid}")
	public ResponseEntity<String> removePolicy(@PathVariable Long pid){
		return  ResponseEntity.ok(service.removePolicy(pid));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getUsersInfo")
	public ResponseEntity<List<UserInfoDto>> getUserInfo(){
		return ResponseEntity.ok(service.getAllUsersInfo());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllVerfications")
	public ResponseEntity<List<AdminVerficationResponseDto>> getAllVerification(){
		return ResponseEntity.ok(service.getAllVerification());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestBody StatusUpdateDto request){
		return ResponseEntity.ok(service.updateStatus(request));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteNotification")
	public ResponseEntity<String> deleteNotification(@PathVariable Long id){
		return ResponseEntity.ok(service.deleteNotification(id));
	}
	
}
