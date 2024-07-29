package ru.miro.analytics_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user_statistics")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long totalSteps;

    private Long totalCalories;

    private Long totalSleepHours;

    private Long totalHeartRate;

    private Integer averageHeartRate;

    private Integer countHeartRateDays;

}
