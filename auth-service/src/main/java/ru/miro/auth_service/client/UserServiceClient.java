package ru.miro.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.miro.auth_service.dto.UserDTO;
import ru.miro.auth_service.dto.request.SignUpRequest;
import ru.miro.auth_service.dto.SignUpDTO;

@FeignClient(name = "user-service", url = "http://localhost:8091")
public interface UserServiceClient {

    @PostMapping("/user/add")
    ResponseEntity<HttpStatus> add(@RequestBody SignUpRequest request);

    @GetMapping("/user/getUserByEmail/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String email);

}
