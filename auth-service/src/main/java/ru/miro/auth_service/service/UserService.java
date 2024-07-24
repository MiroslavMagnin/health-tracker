package ru.miro.auth_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.miro.auth_service.model.User;

import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
    void save(User user);
    Optional<User> findByEmail(String email); // Since email is unique, we'll find users by email
}
