package ru.miro.auth_service.service;

import ru.miro.auth_service.dao.request.SignUpRequest;
import ru.miro.auth_service.dao.request.SigninRequest;
import ru.miro.auth_service.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
