package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.UserServiceint;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
   private UserServiceint userService;

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setProfilePictureUrl(userDetails.getProfilePictureUrl());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/uploadProfilePicture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Code to save the file to a storage service and get the URL
        String fileUrl = saveFile(file);
        user.setProfilePictureUrl(fileUrl);
        userRepository.save(user);

        return ResponseEntity.ok(fileUrl);
    }

    // Method to save file
    private String saveFile(MultipartFile file) {
        // Implementation for saving the file and returning the URL
        // This can involve saving the file to a local directory or a cloud storage
        return "URL_of_the_saved_file";
    }

    @GetMapping("/nearest-moderators")
    public List<User> getNearestModerators(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestParam Double latitude,
                                           @RequestParam Double longitude) {
        return userService.findNearestModerators(latitude, longitude);
    }
}

