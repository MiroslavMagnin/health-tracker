package ru.miro.user_service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.miro.user_service.dto.UserDTO;
import ru.miro.user_service.exception.UserNotFoundException;
import ru.miro.user_service.service.UsersService;

@Component
@RequiredArgsConstructor
public class UserDTOValidator implements Validator {

    private final UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (userDTO.getEmail() != null) {
            try {
                usersService.findOne(userDTO.getEmail());
                errors.rejectValue("email", "", "This email address is already in use");
            } catch (UserNotFoundException e) {
                return;
            }
        }
    }

}
