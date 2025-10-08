package com.insurence.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insurence.dto.AdminVerficationResponseDto;
import com.insurence.dto.IssueRequestDto;
import com.insurence.dto.IssueResponseDto;
import com.insurence.dto.PResponse;
import com.insurence.dto.PolicyRegisterDto;
import com.insurence.dto.PolicyResponseDto;
import com.insurence.dto.PolicyUpdateDto;
import com.insurence.dto.StatusUpdateDto;
import com.insurence.dto.UserInfoDto;
import com.insurence.dto.UserLoginDto;
import com.insurence.dto.UserRegisterDto;
import com.insurence.dto.UserResponseDto;
import com.insurence.entity.IssuePolicy;
import com.insurence.entity.Policy;
import com.insurence.entity.PolicyStatus;
import com.insurence.entity.User;
import com.insurence.entity.UserNotifications;
import com.insurence.entity.UserVerification;
import com.insurence.entity.VerficationStatus;
import com.insurence.error.IncorrectPassword;
import com.insurence.error.NoPoliciesFoundException;
import com.insurence.error.PolicyAlreadyExistsException;
import com.insurence.error.UserAlreadyExits;
import com.insurence.error.UserNotFoundException;
import com.insurence.repository.IssuPolicyRepository;
import com.insurence.repository.NotificationRepository;
import com.insurence.repository.PolicyRepository;
import com.insurence.repository.UserRepo;
import com.insurence.repository.VerificationRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepo uRepo;
	@Autowired
	private PolicyRepository pRepo;
	@Autowired
	private IssuPolicyRepository iRepo;
	@Autowired
	private VerificationRepository vRepo;
	@Autowired
	private NotificationRepository nRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserResponseDto registerAdmin(UserRegisterDto request) {
		Optional<User> existingUser = uRepo.findByEmail(request.getEmail());
		if(existingUser.isPresent()) {
			throw new UserAlreadyExits("User already exits");
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setUserByName(request.getUserName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		List<String> roles = new ArrayList<>();
		
		roles.add("ADMIN");
		roles.add("USER");
		
		user.setRoles(roles);
		
		uRepo.save(user);
		
		return UserResponseDto.builder()
				.email(user.getEmail())
				.userId(user.getUserId())
				.userName(user.getUsername())
				.build();
	}
	
	
	public UserResponseDto login(UserLoginDto request) {
		
		Optional<User> existingUser = uRepo.findByEmail(request.getEmail());
		
		if(existingUser.isEmpty()) {
			throw new UserNotFoundException("Admin not found");
		}
		
		User user = existingUser.get();
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new IncorrectPassword("Wrong Password");
		}
		
		return UserResponseDto.builder()
				.userName(user.getUsername())
				.email(user.getEmail())
				.userId(user.getUserId())
				.build();
	}
	
	
	public List<User> getAllUsers(){
		List<User> users = uRepo.findAll();
		
		if(users == null) {
			throw new UserNotFoundException("No Users found");
		}
		return users;	
	}
	
	public PolicyResponseDto createPolicy(PolicyRegisterDto request) {
		Optional<Policy> policy = pRepo.findByPolicyName(request.getPolicyName());
		
		if(policy.isPresent()) {
			throw new PolicyAlreadyExistsException("Policy already exists");
		}
		
		Policy newPolicy = new Policy();
		
		newPolicy.setDescription(request.getDescription());
		newPolicy.setAmount(request.getPolicyAmount());
		newPolicy.setVehicalType(request.getVehichleType());
		newPolicy.setPolicyName(request.getPolicyName());
		
		pRepo.save(newPolicy);
		
		
		return PolicyResponseDto.builder()
				.policyAmount(newPolicy.getAmount())
				.policyId(newPolicy.getPolicyId())
				.policyName(newPolicy.getPolicyName())
				.description(newPolicy.getDescription())
				.build();
	}


	public List<PResponse> getAllIssuedPolicies() {
		List<IssuePolicy> issuedPolicies = iRepo.findAll();
		
		if(issuedPolicies == null) {
			throw new NoPoliciesFoundException("No Policies issued");
		}
		
		List<PResponse> responses = new ArrayList<>();
		
		
		
		issuedPolicies.stream()
		.forEach(policy -> {
			PResponse res = new PResponse();
			res.setPid(policy.getPolicyId());
			User user = uRepo.findById(policy.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
			res.setUsername(user.getUsername());
			res.setVehicleType(policy.getVehichalType());
			res.setInstallmentType(policy.getInstallmentType());
			res.setInstallmentsLeft(policy.getInstallmentsLeft());
			
			responses.add(res);
		});
		
		return responses;
	}


	public UserResponseDto registerUser(UserRegisterDto register) {

		Optional<User> existingUser = uRepo.findByEmail(register.getEmail());
		if (!existingUser.isEmpty()) {
			throw new UserAlreadyExits("User already exits by id " + register.getEmail());
		}
		User user = new User();
		user.setUserByName(register.getUserName());
		user.setEmail(register.getEmail());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		List<String> roles = new ArrayList<>();
		roles.add("USER");
		user.setRoles(roles);
		uRepo.save(user);

		return UserResponseDto.builder().email(user.getEmail()).userName(user.getUserByName()).userId(user.getUserId())
				.build();
	}


	public PolicyResponseDto updatePolicy(PolicyUpdateDto request) {
		Optional<Policy> existingPolicy = pRepo.findById(request.getPid());
		if(!existingPolicy.isPresent()) {
			throw new NoPoliciesFoundException("Policy Not found");
		}
		
		
		Policy policy = existingPolicy.get();
		policy.setDescription(request.getDescription());
		policy.setAmount(request.getPolicyAmount());
		policy.setVehicalType(request.getVehichleType());
		policy.setPolicyName(request.getPolicyName());
		
		pRepo.save(policy);
		
		return PolicyResponseDto.builder()
				.description(policy.getDescription())
				.policyName(policy.getPolicyName())
				.policyAmount(policy.getAmount())
				.vehicleType(policy.getVehicalType())
				.build();
	}


	public String removePolicy(Long pid) {
		Optional<Policy> exstingPolicy = pRepo.findById(pid);
		if(!exstingPolicy.isPresent()) {
			throw new NoPoliciesFoundException("Policy not found");
		}
		 pRepo.deleteById(pid);
		 return "Policy Deleted Successfully";
	}


	public List<UserInfoDto> getAllUsersInfo() {
		
		List<User> usersList = uRepo.findAll();
		
		if(usersList.isEmpty()) {
			throw new UserNotFoundException("No Users");
		}
		
		List<UserInfoDto> users = new ArrayList<>();
		
		usersList.forEach(user -> {
			UserInfoDto userInfo = new UserInfoDto();
			userInfo.setUserId(user.getUserId());
			userInfo.setUserName(user.getUserByName());
			userInfo.setEmail(user.getEmail());
			
			List<IssuePolicy> activePolicyList = iRepo.findByUserId(user.getUserId());
			Integer activePolicies = activePolicyList.size();
			userInfo.setActivePolicies(activePolicies);
			
			users.add(userInfo);
		});
		
		
		return users;
	}


	public List<UserVerification> getAllVerifications() {
		List<UserVerification> verificationsList = vRepo.findAll();
		
		if(verificationsList.isEmpty()) {
			throw new NoPoliciesFoundException("No Verifications found");
		}
		return verificationsList;
	}


	public List<AdminVerficationResponseDto> getAllVerification() {
		
		List<UserVerification> requestList = vRepo.findAll();
		
		List<AdminVerficationResponseDto> respList = new ArrayList<>();
		
		requestList.forEach(request -> {
			AdminVerficationResponseDto resp = new AdminVerficationResponseDto();
			
			User user = uRepo.findById(request.getUserId())
					.orElseThrow(() -> new UserNotFoundException("User not found"));
			List<IssuePolicy> issueList = iRepo.findByUserId(user.getUserId());
			if(issueList.isEmpty()) {
				resp.setActivePolicies(0);
			} else {
				resp.setActivePolicies(issueList.size());
			}
			
			resp.setId(request.getId());
			resp.setUserId(request.getUserId());
			resp.setUserName(user.getUsername());
			resp.setEmail(user.getEmail());
			
			Policy policy = pRepo.findById(request.getPolicyId())
					.orElseThrow(() -> new PolicyAlreadyExistsException("Policy not found"));
			
			resp.setPolicyId(policy.getPolicyId());
			resp.setPolicyName(policy.getPolicyName());
			resp.setAnnualAmount(policy.getAmount());
			
			resp.setVehicleType(policy.getVehicalType());
			resp.setRegNumber(request.getRegNumber());
			
			resp.setDocType(request.getDocType());
			resp.setDocNumber(request.getDocNumber());
			
			resp.setStatus(VerficationStatus.PENDING);
			resp.setRequestedDate(request.getRequestedDate());
			
			respList.add(resp);
			
		});
		
		return respList;
	}


	public String updateStatus(StatusUpdateDto request) {
		
		UserVerification uv = vRepo.findById(request.getVerificationId())
				.orElseThrow(() -> new RuntimeException("Request not found"));
		uv.setStatus(request.getStatus());
		
		UserNotifications notifications = new UserNotifications();
		
		Optional<User> user = uRepo.findById(request.getUserId());
		if(user.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		
		Policy policy = pRepo.findById(uv.getPolicyId())
				.orElseThrow(() -> new NoPoliciesFoundException("Policy not found"));
		
		Optional<UserVerification> verification = vRepo.findById(request.getVerificationId());
		if(verification.isEmpty()) {
			throw new NoPoliciesFoundException("No Verifiactions Requests");
		}
		
		UserVerification exVerification = verification.get();
		
		String result = null;
		
		System.out.println("Verification Status::" + request.getStatus());
		
		if(VerficationStatus.CANCELED.equals(request.getStatus())) {
			result = " is canceled due to " + request.getRemarks() +".";
		} else {
			result = " is verified successfully pleace Pay to get the policy.";
		}
		
		String msg = "Hii " +
				user.get().getUsername() + 
				" your Request for Policy Id " + exVerification.getPolicyId() +
				" is " + result;
		
		notifications.setUser(user.get());
		notifications.setMsg(msg);
		LocalDateTime sentAt = LocalDateTime.now();
		notifications.setSentAt(sentAt);
		notifications.setIsSeen(false);
		nRepo.save(notifications);
		
		IssueRequestDto issueDto = new IssueRequestDto();
		issueDto.setUserId(uv.getUserId());
		issueDto.setPolicyId(uv.getPolicyId());
		issueDto.setVehicalType(policy.getVehicalType());
		issueDto.setRegNo(uv.getRegNumber());
		issueDto.setDocumentType(uv.getDocType());
		issueDto.setDocumentNumber(uv.getDocNumber());
		issueDto.setInstallmentType(uv.getInstallmentType());
		issueDto.setAmountPaid(0.0);
		
		if(VerficationStatus.VERIFIED.equals(request.getStatus())) {
			issueDto.setPolicyStatus(PolicyStatus.VERIFIED);
			exVerification.setVStatus(false);
		} else if(VerficationStatus.CANCELED.equals(request.getStatus())) {
			issueDto.setPolicyStatus(PolicyStatus.CANCELED);		
			exVerification.setVStatus(false);
		}
		
		issue(issueDto);
		
		System.out.println(issueDto);
		
		return "Verified Successfully";
	}


	public String deleteNotification(Long id) {
		nRepo.deleteById(id);
		return null;
	}	
	
	
	private IssueResponseDto issue(IssueRequestDto request) {
			
			
		    Optional<IssuePolicy> existingIssue = iRepo
		            .findByUserIdAndPolicyIdAndVehichalType(
		                request.getUserId(),
		                request.getPolicyId(),
		                request.getVehicalType()
		            );
	
		    if (existingIssue.isPresent()) {
		        throw new PolicyAlreadyExistsException("Policy is already taken by the user.");
		    }
	
		    
		    Policy policy = pRepo.findById(request.getPolicyId())
		            .orElseThrow(() -> new NoPoliciesFoundException("Policy not found"));
	
//		    double baseAmount = policy.getPolicyAmt();
//		    double interestRate = request.getInstallmentType().getIntrestRate();
//		    int totalInstallments = request.getInstallmentType().getInstallments();
//	
//		    double totalAmount = baseAmount + (baseAmount * interestRate);
//		    double amountPaid = request.getAmountPaid();
//		    double perInstallmentAmount = totalAmount / totalInstallments;
//	
//		    int installmentsPaid = (int) (amountPaid / perInstallmentAmount);
//		    int installmentsLeft = totalInstallments - installmentsPaid;
//		    double remainingAmount = totalAmount - amountPaid;
	
		    IssuePolicy newPolicy = new IssuePolicy();
		    newPolicy.setPolicyId(request.getPolicyId());
		    newPolicy.setUserId(request.getUserId());
		    newPolicy.setVehichalType(request.getVehicalType());
		    newPolicy.setInstallmentType(request.getInstallmentType());
		    
//		    newPolicy.setAmountPaid(amountPaid);
//		    newPolicy.setInstallmentsLeft(installmentsLeft);
//		    newPolicy.setRemainingAmount(remainingAmount);
		    
		    newPolicy.setDocumentNumber(request.getDocumentNumber());
		    newPolicy.setDocumentType(request.getDocumentType());
		    newPolicy.setRegistrationNo(request.getRegNo());
//		    newPolicy.setPolicyEndDate(newPolicy.getPolicyEndDate());
		    newPolicy.setPolicyStatus(request.getPolicyStatus());
	
		    
		    iRepo.save(newPolicy);
	
		    return IssueResponseDto.builder()
		            .issueId(newPolicy.getIssueId())
		            .policyId(newPolicy.getPolicyId())
		            .userId(newPolicy.getUserId())
		            .vehicalType(newPolicy.getVehichalType())
		            .amountPaid(newPolicy.getAmountPaid())
		            .remainingAmount(newPolicy.getRemainingAmount())
		            .installmentsLeft(newPolicy.getInstallmentsLeft())
//		            .totalAmount(totalAmount)
		            .build();
		}


}
