package ru.miro.auth_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SignUpDTO {

    private Long id;

    private String name;

    private String email;

}
