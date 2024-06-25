package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.services.PasswordResetService;

import java.util.Map;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        passwordResetService.resetPassword(email);
        return ResponseEntity.ok("Password reset code sent to your email.");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateResetToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        boolean isValid = passwordResetService.validateResetToken(email, token);
        return ResponseEntity.ok(isValid ? "Valid token." : "Invalid or expired token.");
    }

    @PostMapping("/change")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        passwordResetService.changePassword(email, token, newPassword);
        return ResponseEntity.ok("Password changed successfully.");
    }
}
