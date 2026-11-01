
package com.stockmanagement.server.service;

import com.stockmanagement.server.entity.User;
import com.stockmanagement.server.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import com.stockmanagement.server.dto.UserRequest;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create user
    public User addUser(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    // Update user
    public User updateUser(Long id, UserRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUsername() != null &&
                !request.getUsername().equals(existingUser.getUsername())) {

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            existingUser.setUsername(request.getUsername());
        }

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        if (request.getRole() != null) {

            if (existingUser.getRole() == User.Role.MANAGER &&
                    request.getRole() == User.Role.CASHIER &&
                    userRepository.countByRole(User.Role.MANAGER) <= 1) {

                throw new RuntimeException(
                        "Cannot change the role of the last manager"
                );
            }

            existingUser.setRole(request.getRole());
        }
        return userRepository.save(existingUser);
    }

    // Delete user
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == User.Role.MANAGER &&
                userRepository.countByRole(User.Role.MANAGER) <= 1) {

            throw new RuntimeException(
                    "Cannot delete the last manager account"
            );
        }

        userRepository.delete(user);
    }
    
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
