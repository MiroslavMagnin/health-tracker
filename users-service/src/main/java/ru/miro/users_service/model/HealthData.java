package ru.miro.users_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HealthData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "date")
    @JsonFormat(pattern = "yyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;

    @Column(name = "steps")
    @Min(value = 0, message = "The count of the steps should be greater than 0")
    private Integer steps;

    @Column(name = "calories")
    @Min(value = 0, message = "The count of the calories should be greater than 0")
    private Integer calories;

    @Column(name = "sleep_hours")
    @Min(value = 0, message = "The count of the sleep hours should be greater than 0")
    @Max(value = 24, message = "The count of the sleep hours should be less than 24")
    private Byte sleepHours;

    @Column(name = "heart_rate")
    @Min(value = 0, message = "The heart rate should be greater than 0")
    private Integer heartRate;

    private long createdAt;
    private long updatedAt;

}
