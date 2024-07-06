package tn.esprit.projetPI.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Path;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.repository.RoleRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.security.jwt.JwtUtils;
import tn.esprit.projetPI.services.EmailService;
import tn.esprit.projetPI.services.FirebaseStorageServiceipm;
import tn.esprit.projetPI.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import tn.esprit.projetPI.models.ERole;
import tn.esprit.projetPI.models.Role;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.payload.request.LoginRequest;
import tn.esprit.projetPI.payload.request.SignupRequest;
import tn.esprit.projetPI.payload.response.JwtResponse;
import tn.esprit.projetPI.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
    UserRepository userRepository;

	@Autowired
    RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
    JwtUtils jwtUtils;
	@Autowired
	private EmailService emailService;
@Autowired
private FirebaseStorageServiceipm firebaseStorageService;
	@Autowired
	private ObjectMapper objectMapper; // For JSON parsing

	/*@GetMapping("/user")
	public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
		return principal;
	}*/
	@GetMapping("/send-email")
	public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
		emailService.sendSimpleEmail(to, subject, text);
		return "Email sent successfully";
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// Authenticate user credentials
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// Set authenticated user in security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Generate JWT token
		String jwt = jwtUtils.generateJwtToken(authentication);

		// Get user details
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		// Check if user is blocked (pseudo-code)

		/*if (userDetails.isBlocked()) {
			// If user is blocked, return a response indicating blocked status
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("User is blocked. Please contact support for assistance.");
		}*/

		// If not blocked, continue with normal JWT response
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
                userDetails.isBlocked(),
				roles,
				userDetails.getProfilePictureUrl()
				));
	}

	@PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> registerUser(@RequestParam("signUpRequest") String signUpRequestString,
										  @RequestPart("file") MultipartFile file) {
		System.out.println("Received signUpRequestString: " + signUpRequestString);

		SignupRequest signUpRequest;
		try {
			signUpRequest = objectMapper.readValue(signUpRequestString, SignupRequest.class);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid signUpRequest data"));
		}

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Upload profile picture to Firebase
		String profilePictureUrl = null;
		try {
			profilePictureUrl = firebaseStorageService.uploadFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		user.setProfilePictureUrl(profilePictureUrl);

		// Set latitude and longitude
		user.setLatitude(signUpRequest.getLatitude());
		user.setLongitude(signUpRequest.getLongitude());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					case "MODERATOR":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		user.setBlocked(true);

		// Generate verification token
		String verificationToken = UUID.randomUUID().toString();
		user.setVerificationToken(verificationToken);

		userRepository.save(user);

		// Send verification email
		String verificationUrl = "http://localhost:4200/#/verif?token=" + verificationToken;
		emailService.sendSimpleEmail(user.getEmail(), "Email Verification", "Please verify your email by clicking on the following link: " + verificationUrl);

		return ResponseEntity.ok(new MessageResponse("User registered successfully! Please check your email to verify your account."));
	}

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
		User user = userRepository.findByVerificationToken(token)
				.orElseThrow(() -> new RuntimeException("Error: Invalid verification token."));

		user.setBlocked(false);
		user.setVerificationToken(null);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User verified successfully!"));
	}



	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();  // Invalidate the session
		SecurityContextHolder.clearContext();  // Clear the security context

		// Clear cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
	}


}
