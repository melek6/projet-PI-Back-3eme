package tn.esprit.projetPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email")
		})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	private boolean blocked;
	private String profilePictureUrl;

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	@NotBlank
	@Size(max = 120)
	private String password;


//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
////	@JsonManagedReference
//	private Set<Formation> formations;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))

	private Set<Role> roles = new HashSet<>();


	@OneToMany(mappedBy = "userFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Message> userFromMessages;

	@OneToMany(mappedBy = "userTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Message> userToMessages;


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Quiz> quizzes;

	//@OneToOne(mappedBy = "user")
	//private Tentative tentative;
	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

    public User(Long userId) {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public List<Message> getUserFromMessages() {
		return userFromMessages;
	}

	public void setUserFromMessages(List<Message> userFromMessages) {
		this.userFromMessages = userFromMessages;
	}

	public List<Message> getUserToMessages() {
		return userToMessages;
	}

	public void setUserToMessages(List<Message> userToMessages) {
		this.userToMessages = userToMessages;
	}
}
