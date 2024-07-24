package ru.miro.auth_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
public class SignUpRequest {

    @NotNull(message = "The name shouldn't be empty")
    @Size(min = 2, max = 100, message = "The length of the name should be from 2 to 100 characters")
    private String name;

    @NotNull(message = "The birt date shouldn't be empty")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;

    @NotNull(message = "The email shouldn't be empty")
    @Email(message = "The email should be match the format (example: ivan_ivanov@gmail.com)")
    private String email;

    @NotNull(message = "The password shouldn't be empty")
    private String password;

    @Min(value = 0, message = "The weight should be greater than 0")
    private Integer weight;

    @Min(value = 0, message = "The height should be greater than 0")
    private Integer height;

}
