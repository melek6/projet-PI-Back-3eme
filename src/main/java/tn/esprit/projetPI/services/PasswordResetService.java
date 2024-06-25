package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.PasswordResetToken;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.PasswordResetTokenRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        String token = UUID.randomUUID().toString().substring(0, 6); // Generating a 6-digit code
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600000)); // 1 hour expiry
        tokenRepository.save(resetToken);

        emailService.sendSimpleEmail(user.getEmail(), "Password Reset Code", "Your password reset code is: " + token);
    }

    public boolean validateResetToken(String email, String token) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        return resetToken != null && resetToken.getUser().getId().equals(user.getId()) && resetToken.getExpiryDate().after(new Date());
    }

    public void changePassword(String email, String token, String newPassword) {
        if (!validateResetToken(email, token)) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        User user = userRepository.findByEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        tokenRepository.delete(resetToken);
    }
}
