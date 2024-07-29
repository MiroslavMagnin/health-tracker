package ru.miro.analytics_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.miro.analytics_service.dto.HealthDataDTO;
import ru.miro.analytics_service.exception.UserNotFoundException;
import ru.miro.analytics_service.model.UserStatistics;
import ru.miro.analytics_service.repository.UsersStatisticsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersStatisticsService {

    private final UsersStatisticsRepository usersStatisticsRepository;

    public List<UserStatistics> findAll() {
        return usersStatisticsRepository.findAll();
    }

    public UserStatistics findByUserId(long id) {
        return usersStatisticsRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void save(HealthDataDTO healthDataDTO) {
        Optional<UserStatistics> user = usersStatisticsRepository.findByUserId(healthDataDTO.getUserId());

        // Create userStatics based on healthDataDTO
        if (user.isEmpty()) {
            UserStatistics userStatistics = UserStatistics.builder()
                    .userId(healthDataDTO.getUserId())
                    .totalSteps((long) healthDataDTO.getSteps())
                    .totalCalories((long) healthDataDTO.getCalories())
                    .totalSleepHours((long) healthDataDTO.getSleepHours())
                    .totalHeartRate((long) healthDataDTO.getHeartRate())
                    .countHeartRateDays(1)
                    .averageHeartRate(healthDataDTO.getHeartRate())
                    .build();
            usersStatisticsRepository.save(userStatistics);
        }
        // Refresh data if the user is already exist
        else {
            UserStatistics currentUser = user.get();
            UserStatistics updatedUserStatistics = UserStatistics.builder()
                            .id(currentUser.getId())
                            .userId(currentUser.getUserId())
                            .totalSteps(currentUser.getTotalSteps() + healthDataDTO.getSteps())
                            .totalCalories(currentUser.getTotalCalories() + healthDataDTO.getCalories())
                            .totalSleepHours(currentUser.getTotalSleepHours() + healthDataDTO.getSleepHours())
                            .countHeartRateDays(currentUser.getCountHeartRateDays() + 1)
                            .totalHeartRate(currentUser.getTotalHeartRate() + healthDataDTO.getHeartRate())
                            .averageHeartRate((int) ((currentUser.getTotalHeartRate() + healthDataDTO.getHeartRate()) / (currentUser.getCountHeartRateDays() + 1)))
                            .build();
            usersStatisticsRepository.save(updatedUserStatistics);
        }
    }

}
