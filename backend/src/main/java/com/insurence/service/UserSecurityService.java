package com.insurence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.insurence.repository.UserRepo;

@Service
public class UserSecurityService implements UserDetailsService {
	
	@Autowired
	private UserRepo uRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return uRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

}
