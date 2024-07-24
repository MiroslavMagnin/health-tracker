package ru.miro.jwt_service.service;

import ru.miro.jwt_service.dao.request.SignUpRequest;
import ru.miro.jwt_service.dao.request.SigninRequest;
import ru.miro.jwt_service.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
