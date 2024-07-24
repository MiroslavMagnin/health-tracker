package ru.miro.auth_service.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.miro.auth_service.service.AuthenticationService;
import ru.miro.auth_service.service.JwtService;
import ru.miro.auth_service.dao.request.SignUpRequest;
import ru.miro.auth_service.dao.request.SigninRequest;
import ru.miro.auth_service.dao.response.JwtAuthenticationResponse;
import ru.miro.auth_service.model.Role;
import ru.miro.auth_service.model.User;
import ru.miro.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import ru.miro.auth_service.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .weight(request.getWeight())
                .height(request.getHeight())
                .role(Role.USER)
                .build();
        userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
