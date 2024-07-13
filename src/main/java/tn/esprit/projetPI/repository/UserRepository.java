package tn.esprit.projetPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.projetPI.models.ERole;
import tn.esprit.projetPI.models.Role;
import tn.esprit.projetPI.models.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> getByEmail(@NotBlank @Size(max = 50) @Email String email);

	Optional<User> findByVerificationToken(String verificationToken);
	List<User> findByRoles(Role role);

	Boolean existsByUsername(String username);
	User findByEmail(String email);
	Boolean existsByEmail(String email);
	@Query("SELECT u FROM User u JOIN u.roles r WHERE u.blocked = true AND r.name = :roleName")
	List<User> findBlockedUsersByRole(@Param("roleName") ERole roleName);
	@Query("SELECT COUNT(u) FROM User u")
	Long countTotalUsers();

	@Query("SELECT COUNT(u) FROM User u WHERE u.blocked = true")
	Long countBlockedUsers();

	@Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = 'ROLE_MODERATOR'")
	Long countModerators();
}
