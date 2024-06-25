package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.ERole;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.services.UserService;
import tn.esprit.projetPI.services.UserServiceint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {

    @Autowired
    private UserServiceint userService;
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        Set<ERole> roles = new HashSet<>();
        if (userRequest.getRoles() != null) {
            userRequest.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(ERole.ROLE_ADMIN);
                        break;
                    case "moderateur":
                        roles.add(ERole.ROLE_MODERATOR);
                        break;
                    default:
                        roles.add(ERole.ROLE_USER);
                }
            });
        } else {
            roles.add(ERole.ROLE_USER);
        }

        User user = userService.addUser(userRequest.getUsername(), userRequest.getEmail(), userRequest.getPassword(), roles);
        return ResponseEntity.ok(user);
    }



    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id.intValue());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/block/{id}")
    public ResponseEntity<User> blockUser(@PathVariable("id") Long id) {
        User blockedUser = userService.blockUser(id);
        return new ResponseEntity<>(blockedUser, HttpStatus.OK);
    }
}

