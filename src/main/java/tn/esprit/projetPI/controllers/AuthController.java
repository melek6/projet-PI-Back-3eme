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

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.repository.RoleRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.security.jwt.JwtUtils;
import tn.esprit.projetPI.services.EmailService;
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

				roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

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
				case "mod":
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
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	/*@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestPart("signupRequest") SignupRequest signUpRequest,
										  @RequestPart("image") MultipartFile image) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

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
					case "mod":
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

		// Save the image to a local folder or cloud storage and get the URL
		String imageUrl = saveImage(image);
		user.setProfilePictureUrl(imageUrl);

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	private String saveImage(MultipartFile image) {
		if (image.isEmpty()) {
			throw new RuntimeException("Failed to store empty file.");
		}

		try {
			String uploadDir = "user-photos/";
			String originalFilename = image.getOriginalFilename();
			String newFilename = UUID.randomUUID().toString() + "_" + originalFilename;
			Path path = Paths.get(uploadDir + newFilename);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			return path.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file.", e);
		}
	}
*/
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
