package ru.miro.auth_service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import ru.miro.auth_service.exception.ErrorResponse;
import ru.miro.auth_service.exception.ValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream body = response.body().asInputStream()) {
            Map errors =
                    mapper.readValue(IOUtils.toString(body, StandardCharsets.UTF_8), Map.class);
            if (response.status() == 400) {
                return ValidationException.builder()
                        .validationErrors(errors).build();
            } else
                return ErrorResponse
                        .builder()
                        .httpStatus(HttpStatus.valueOf(response.status()))
                        .message((String) errors.get("message"))
                        .build();

        } catch (IOException exception) {
            throw ErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message(exception.getMessage())
                    .build();
        }
    }
}
