// UserServiceImpl.java
package course.service;

import course.entity.User;
import course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;       // For Arrays.stream()

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public User findUserById(Long id) {
        Optional<User> userOptional = repository.findById(id);
        return userOptional.orElse(null); 
    }

    public List<User> findAllByOrderByUsernameAsc() {
        return repository.findAllByOrderByUsernameAsc();
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public void updateUser(User user) {  // Keep for explicit update operations if needed
        repository.save(user);
    }

    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllUsers() {  // Use with caution!
        repository.deleteAll();
    }

    public List<User> findAllUsers() {
        return (List<User>) repository.findAll(); // No cast needed in newer Spring Data JPA versions
    }

    public User encryptPassword(User user) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder(); // New instance for thread safety
        String hashPwd = bc.encode(user.getPassword());
        user.setPassword(hashPwd);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = repository.findByUsername(username);
        if (curruser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(curruser.getRole().split(",")) // Create a Stream<String>
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim().toUpperCase()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                username,
                curruser.getPassword(),
                authorities
        );
    }
}
