package com.insurence.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insurence.configuration.JwtUtil;
import com.insurence.dto.MyPoliciesResponse;
import com.insurence.dto.PaymentRequestDto;
import com.insurence.dto.UserLoginDto;
import com.insurence.dto.UserRegisterDto;
import com.insurence.dto.UserResponseDto;
import com.insurence.dto.VerficationDto;
import com.insurence.entity.IssuePolicy;
import com.insurence.entity.PaymentDetails;
import com.insurence.entity.Policy;
import com.insurence.entity.PolicyStatus;
import com.insurence.entity.User;
import com.insurence.entity.UserNotifications;
import com.insurence.entity.UserVerification;
import com.insurence.entity.VerficationStatus;
import com.insurence.error.NoPoliciesFoundException;
import com.insurence.error.UserAlreadyExits;
import com.insurence.error.UserNotFoundException;
import com.insurence.repository.IssuPolicyRepository;
import com.insurence.repository.PaymentDetailsRepository;
import com.insurence.repository.PolicyRepository;
import com.insurence.repository.UserRepo;
import com.insurence.repository.VerificationRepository;

@Service
public class UserService {

	@Autowired
	private UserRepo uRepo;
	@Autowired
	private PaymentDetailsRepository pdRepo;
	@Autowired
	private IssuPolicyRepository iRepo;
	@Autowired
	private PolicyRepository pRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private VerificationRepository vRepo;
	@Autowired
	private AuthenticationManager authManager;

	public UserResponseDto registerUser(UserRegisterDto register) {

		/*
		 * if (register.getUserName().isBlank() || register.getUserName().isEmpty()) {
		 * throw new RuntimeException("Name should not be empty "); } else if
		 * (register.getEmail().isBlank() || register.getEmail().isBlank()) { throw new
		 * RuntimeException("Please enter email"); } else if
		 * (register.getPassword().isBlank() || register.getPassword().isBlank()) {
		 * throw new RuntimeException("Enter Passord"); }
		 */

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

	public User getUser(Long userId) {
		return uRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found by id " + userId));
	}

	public List<User> getAllUsers() {

		List<User> users = uRepo.findAll();
		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found");
		}

		return users;
	}

	public List<MyPoliciesResponse> getMyPolicies(Long id) {

		User user = uRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found by id " + id));

		List<IssuePolicy> issuesList = iRepo.findByUserId(id);

		if (issuesList.isEmpty()) {
			throw new RuntimeException("No policies are taken");
		}

		List<MyPoliciesResponse> responseList = new ArrayList<>();

		for (IssuePolicy policy : issuesList) {
			System.out.println(policy);
			Policy policyEntity = pRepo.findById(policy.getPolicyId())
					.orElseThrow(() -> new NoPoliciesFoundException("Policy not found with ID: " + policy.getPolicyId()));

//			LocalDate issuedDate = policy.getIssuedAt();
//			LocalDate newDate = issuedDate.plusYears(1).minusDays(1);
			double installmentAmount = (policyEntity.getAmount() + (policyEntity.getAmount()*policy.getInstallmentType().getIntrestRate()))/policy.getInstallmentType().getInstallments();
			
			MyPoliciesResponse response = MyPoliciesResponse.builder()
					.userId(policy.getUserId())
					.policyId(policyEntity.getPolicyId())
					.policyName(policyEntity.getPolicyName())
					.vehicalType(policyEntity.getVehicalType())
					.registrationNo(policy.getRegistrationNo())
					.documentType(policy.getDocumentType())
					.documentNumber(policy.getDocumentNumber())
					.issuedAt(policy.getIssuedAt())
					.nextDueAt(policy.getNextDueAt())
					.installmentType(policy.getInstallmentType())
					.installmentsLeft(policy.getInstallmentsLeft())
					.amountPaid(policy.getAmountPaid())
					.remainingAmount(policy.getRemainingAmount())
//					.endDate(newDate)
					.policyStatus(policy.getPolicyStatus())
					.isPaymentInitialized(policy.isPaymentInitialized())
					.policyAmount(policyEntity.getAmount())
					.installmentAmount(installmentAmount)
					.build();

			System.out.println(response);
			responseList.add(response);

		}

		return responseList;

	}

	public UserResponseDto login(UserLoginDto request) {
		
		User user = uRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);

		String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
		System.out.println("Token::" + token);

		return UserResponseDto.builder().email(user.getEmail()).userId(user.getUserId()).userName(user.getUserByName())
				.token(token).build();
	}
	
	public String verifyUser(VerficationDto request) {
			
			UserVerification verification = new UserVerification();
			verification.setDocNumber(request.getDocNumber());
			verification.setDocType(request.getDocType());
			verification.setUserId(request.getUserId());
			verification.setRegNumber(request.getRegNumber());
			verification.setPolicyId(request.getPolicyId());
			verification.setStatus(VerficationStatus.PENDING);
			verification.setInstallmentType(request.getInstallmentType());
			verification.setVStatus(true);
			
			System.out.println("Verification::" + verification);
			vRepo.save(verification);
			
			return "Sent for verification";
	}

	public List<UserNotifications> getNotifications(Long id) {
		return uRepo.findById(id)
				.map(User::getNotifications)
				.orElseThrow(() -> new UserNotFoundException("User is not found"));
	}

	public String payment(PaymentRequestDto request) {
		
		
	    LocalDate asOfDate = LocalDate.now();

	    IssuePolicy issue = iRepo.findByUserIdAndPolicyId(request.getUserId(), request.getPolicyId())
	            .orElseThrow(() -> new RuntimeException("Policy not taken"));

	    LocalDate settlementDate = issue.getPolicyEndDate();

	    if (asOfDate.isAfter(settlementDate) || asOfDate.isEqual(settlementDate)) {
	        return "Invalid As Of Date";
	    }

	    PaymentDetails paymentDetails = new PaymentDetails();
	    paymentDetails.setAmountPaid(request.getAmount());
	    paymentDetails.setNoOfPayMents(paymentDetails.getNoOfPayMents() + 1);
	    pdRepo.save(paymentDetails);

	    issue.setPaymentInitialized(true);
	    issue.setAmountPaid(issue.getAmountPaid() + request.getAmount());
	    issue.setPaymentDetails(paymentDetails);
	    issue.setIssuedAt(asOfDate);
	    issue.setPolicyStatus(PolicyStatus.ACTIVE);

	    iRepo.save(issue);

	    return "Payment Successful";
	}


}
