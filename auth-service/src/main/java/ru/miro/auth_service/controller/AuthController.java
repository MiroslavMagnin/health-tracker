package ru.miro.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.miro.auth_service.dto.SignUpDTO;
import ru.miro.auth_service.dto.TokenDTO;
import ru.miro.auth_service.service.AuthService;
import ru.miro.auth_service.dto.request.SignUpRequest;
import ru.miro.auth_service.dto.request.SigninRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody SignUpRequest request) {
        System.out.println(request.getName() + " " + request.getEmail() + " " + request.getPassword() +
                " " + request.getBirthDate() + " " + request.getWeight() + " " + request.getHeight());
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authService.signin(request));
    }

}
