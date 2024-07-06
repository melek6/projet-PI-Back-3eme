package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.ERole;
import tn.esprit.projetPI.models.Role;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.RoleRepository;
import tn.esprit.projetPI.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceint{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public User addUser(String username, String email, String password, Set<ERole> roles) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        Set<Role> userRoles = new HashSet<>();
        for (ERole role : roles) {
            Role userRole = roleRepository.findByName(role)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            userRoles.add(userRole);
        }

        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById((long) id);
    }

    @Override
    public User getUserById(long id) {
       return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User blockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isBlocked())
        {user.setBlocked(false);
            emailService.sendSimpleEmail(user.getEmail(),
                    "votre compte a ete debloqué","" );}
        else
        {
            user.setBlocked(true);
            emailService.sendSimpleEmail(user.getEmail(),
                    "votre compte a ete bloqué","" );

        }
        return userRepository.save(user);
    }

    @Transactional
    public List<User> findNearestModerators(Double latitude, Double longitude) {
        Role moderatorRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        List<User> moderators = userRepository.findByRoles(moderatorRole);

        return moderators.stream()
                .filter(user -> user.getLatitude() != null && user.getLongitude() != null)
                .sorted((u1, u2) -> Double.compare(
                        distance(latitude, longitude, u1.getLatitude(), u1.getLongitude()),
                        distance(latitude, longitude, u2.getLatitude(), u2.getLongitude())
                ))
                .collect(Collectors.toList());
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to kilometers

        return distance;
    }
}

