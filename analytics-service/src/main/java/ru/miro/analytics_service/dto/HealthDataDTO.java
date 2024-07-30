package ru.miro.analytics_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class HealthDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;

    @Min(value = 0, message = "The count of the steps should be greater than 0")
    private Integer steps;

    @Min(value = 0, message = "The count of the calories should be greater than 0")
    private Integer calories;

    @Min(value = 0, message = "The count of the sleep hours should be greater than 0")
    @Max(value = 24, message = "The count of the sleep hours should be less than 24")
    private Byte sleepHours;

    @Min(value = 0, message = "The heart rate should be greater than 0")
    private Integer heartRate;

}
