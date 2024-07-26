package ru.miro.auth_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ru.miro.auth_service.client.UsersServiceClient;
import ru.miro.auth_service.dto.TokenDTO;
import ru.miro.auth_service.exception.WrongCredentialsException;
import ru.miro.auth_service.dto.request.SignUpRequest;
import ru.miro.auth_service.dto.request.SigninRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsersServiceClient usersServiceClient;
    private final JwtService jwtService;

    public HttpStatus signup(SignUpRequest request) {
        usersServiceClient.add(request);
        return HttpStatus.CREATED;
    }

    public TokenDTO signin(SigninRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (authenticate.isAuthenticated()) {
            return TokenDTO
                    .builder()
                    .token(jwtService.generateToken(request.getEmail()))
                    .build();
        } else throw new WrongCredentialsException("Wrong credentials");

    }
}
