package ru.miro.users_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.miro.users_service.dto.UserDTO;
import ru.miro.users_service.exception.UserNotCreatedException;
import ru.miro.users_service.model.User;
import ru.miro.users_service.service.UsersService;
import ru.miro.users_service.util.UserDTOValidator;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final UserDTOValidator userDTOValidator;

    @GetMapping()
    public List<User> getUsers() {
        return usersService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        return usersService.findOne(id);
    }

    @GetMapping("/getUserByEmail/{email}")
    public User getUser(@PathVariable("email") String email) {
        return usersService.findOne(email);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        userDTOValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new UserNotCreatedException(errorMessage.toString());
        }

        usersService.save(userDTO);
        return HttpStatus.CREATED;
    }

    @PatchMapping("/{id}/update")
    public HttpStatus updateUser(@PathVariable("id") long id,
                                 @RequestBody UserDTO userDTO) {
        usersService.update(id, userDTO);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteUser(@PathVariable("id") long id) {
        usersService.delete(id);
        return HttpStatus.OK;
    }

}
