package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.Candidature;
import tn.esprit.projetPI.models.ERole;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.payload.response.MessageResponse;

import java.util.List;
import java.util.Set;

public interface UserServiceint {
    User addUser(String username, String email, String password, Set<ERole> roles);
    User updateUser(User user);
    void deleteUser(int id);
    public User updateUser(Long id, String username, String email, String phone, String adresse) ;
    public List<User> getBlockedModerators();
        User getUserById(long id);
    List<User> getAllUsers();
    public User blockUser(Long id);
    public List<User> findNearestModerators(Double latitude, Double longitude);
    public MessageResponse changePassword(Long userId, String oldPassword, String newPassword) ;
    public Long getTotalUsers() ;

    public Long getBlockedUsers() ;

    public Long getModerators() ;
}
