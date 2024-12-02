// User.java (course.entity package)
package course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 3, message = "Username is invalid")
    @Column(name = "username", nullable = false, unique = true) // Added unique constraint
    private String username;

    @Size(min = 4, message = "Password is invalid")
    @Column(name = "password", nullable = false)
    private String password;

    @Transient // This should not be a column in the database
    private String passwordCheck;

    @Column(name = "role")
    private String role;

    // For JPA/Hibernate
    public User(){}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
