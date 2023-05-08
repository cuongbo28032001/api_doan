package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Constants;
import com.example.demo.exception.TokenRefreshException;
import com.example.demo.model.ERole;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.Role;
import com.example.demo.model.Thuoc;
import com.example.demo.model.User;
import com.example.demo.payload.request.BaseRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.request.TokenRefreshRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.payload.response.ResponseObj;
import com.example.demo.payload.response.TokenRefreshResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.service.UserDetailsImpl;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private UserService userService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

		User user = userRepository.findByUsernameAndDeleted(loginRequest.getUsername(), Constants.Y);
		if(user != null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is not found!"));
		}

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		String jwt = jwtUtils.generateJwtToken(userDetails);
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		return ResponseEntity.ok(new JwtResponse(Constants.OK, "", jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {

		System.err.println(signupRequest.toString());
//		 Lấy thông tin authen từ header
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> rolesTest = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
//		 Kiểm tra xem user có quyền admin hay không
		if(!rolesTest.contains("ROLE_ADMIN")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: You don't have permission to create new user!"));
		}
		
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if(userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		// Create new user account
		User user = new User(signupRequest.getName(),
				signupRequest.getUsername(),
				signupRequest.getEmail(),
				passwordEncoder.encode(signupRequest.getPassword()),
				signupRequest.getPhone(),
				signupRequest.getSex(),
				signupRequest.getBirthDay(),
				signupRequest.getCccd());

		Set<String> strRoles = signupRequest.getRole();
		
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null || strRoles.isEmpty()) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin": {
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					}
					case "mod": {
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						break;
					}
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
		String refreshToken = tokenRefreshRequest.getRefreshToken();
		
		
		return refreshTokenService.findByToken(refreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, refreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
	}
	
	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Long userId = userDetails.getId();
	    refreshTokenService.deleteByUserId(userId);
	    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}

	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody BaseRequest baseRequest) {

		// Kiểm tra user thực hiện có quyền admin hay không
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> rolesTest = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		if(!rolesTest.contains("ROLE_ADMIN")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: You don't have permission to search user!"));
		}

		ResponseObj resp = this.userService.search(baseRequest);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody User user) {

		// Kiểm tra user thực hiện có quyền admin hay không
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> rolesTest = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		if(!rolesTest.contains("ROLE_ADMIN")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: You don't have permission to update user!"));
		}

		ResponseObj resp = this.userService.update(user);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> rolesTest = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		System.err.println(rolesTest);
		if(!rolesTest.contains("ROLE_ADMIN")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: You don't have permission to get user!"));
		}
		ResponseObj resp = this.userService.getById(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
	
	@DeleteMapping("/status/{id}")
	public ResponseEntity<?> status(@PathVariable Long id) {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> rolesTest = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
//		System.err.println(rolesTest);
//		if(!rolesTest.contains("ROLE_ADMIN")) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: You don't have permission to get user!"));
//		}
		
		ResponseObj resp = this.userService.status(id);
		HttpHeaders headers = new HttpHeaders();
		return ResponseEntity.ok().headers(headers).body(resp);
	}
}
