package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Candidature;
import tn.esprit.projetPI.models.ERole;
import tn.esprit.projetPI.models.User;

import java.util.List;
import java.util.Set;

public interface UserServiceint {
    User addUser(String username, String email, String password, Set<ERole> roles);
    User updateUser(User user);
    void deleteUser(int id);
User getUserById(long id);
    List<User> getAllUsers();
    public User blockUser(Long id);
    public List<User> findNearestModerators(Double latitude, Double longitude);
}
