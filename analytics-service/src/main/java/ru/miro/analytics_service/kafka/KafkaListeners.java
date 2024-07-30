package ru.miro.analytics_service.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.miro.analytics_service.dto.HealthDataDTO;
import ru.miro.analytics_service.service.UsersStatisticsService;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private final UsersStatisticsService usersStatisticsService;
    private final Gson gson;

    @KafkaListener(topics = "healthData", groupId = "groupId")
    void healthDataListener(String data) {
        HealthDataDTO healthDataDTO = gson.fromJson(data, HealthDataDTO.class);

        usersStatisticsService.save(healthDataDTO);
        log.info("Listener received: healthDataDTO - with userId=" + healthDataDTO.getUserId() + " 🎉");
    }
}
