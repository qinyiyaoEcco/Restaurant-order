package lab28.group4.asm1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lab28.group4.asm1.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    protected User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public Role getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
