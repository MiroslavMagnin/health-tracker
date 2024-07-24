package ru.miro.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {

    private String token;

}
