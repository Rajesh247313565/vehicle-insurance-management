package com.insurence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurence.dto.PolicyRegisterDto;
import com.insurence.dto.PolicyResponseDto;
import com.insurence.entity.Policy;
import com.insurence.service.PolicyService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/policy")
public class PolicyController {

	@Autowired
	private PolicyService pService;
	
	@GetMapping("/getAllPolicies")
	public ResponseEntity<List<Policy>> getAllPolicies(){
		return ResponseEntity.ok(pService.getAllPolicies());
	}
	
	

}
