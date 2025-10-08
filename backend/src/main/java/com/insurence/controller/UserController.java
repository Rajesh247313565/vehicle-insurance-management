package com.insurence.controller;

import java.util.List;

import com.insurence.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.insurence.configuration.JwtUtil;
import com.insurence.entity.User;
import com.insurence.entity.UserNotifications;
import com.insurence.error.UserNotFoundException;
import com.insurence.repository.UserRepo;
import com.insurence.service.PolicyService;
import com.insurence.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService uService;
	@Autowired
	private PolicyService pService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepo uRepo;
	
	
	@PostMapping("/login")
	public ResponseEntity<UserResponseDto> loginUser(@Valid @RequestBody UserLoginDto request){
		
		return ResponseEntity.ok(uService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> RegisterUser(@Valid @RequestBody UserRegisterDto request){

		return ResponseEntity.ok(uService.registerUser(request));
	}
	
	
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Long userId){
		return ResponseEntity.ok(uService.getUser(userId));
	}

	@GetMapping("/myPolicies/{id}")
	public ResponseEntity<List<MyPoliciesResponse>> getMyPolicies(@PathVariable Long id){
		return ResponseEntity.ok(uService.getMyPolicies(id));
	}
	
	@PostMapping("/issuePolicy")
	public ResponseEntity<IssueResponseDto> issuePolicy(@RequestBody IssueRequestDto request){
		return ResponseEntity.ok(pService.issue(request));
	}
	
	
	@PostMapping("/verifyUser")
	public ResponseEntity<String> verifyUser(@RequestBody VerficationDto request){
		return ResponseEntity.ok(uService.verifyUser(request));
	}
	
	/*
	 * @GetMapping("/me") public ResponseEntity<UserResponseDto>
	 * getUserFromToke(Authentication authentication){ User user = (User)
	 * authentication.getPrincipal(); }
	 */
	
	@GetMapping("/getNotifications/{id}")
	public ResponseEntity<List<UserNotifications>> getAllNotifications(@PathVariable Long id){
		return ResponseEntity.ok(uService.getNotifications(id));
	}
	
	@GetMapping("/me")
	public ResponseEntity<UserResponseDto> getCurrentUser(Authentication authentication) {
		 String email = (String) authentication.getPrincipal(); // principal is String
		 User user = uRepo.findByEmail(email)
		                     .orElseThrow(() -> new UserNotFoundException("User not found"));
	    return ResponseEntity.ok(UserResponseDto.builder()
	    		.email(user.getEmail())
	    		.userId(user.getUserId())
	    		.userName(user.getUsername())
	    		.build()
	    		);
	}
	
	@PostMapping("/payment")
	public ResponseEntity<String> payment(@RequestBody PaymentRequestDto request){
		System.out.println("service::" + request);
		return ResponseEntity.ok(uService.payment(request));
		
	}
	
	
	
}
