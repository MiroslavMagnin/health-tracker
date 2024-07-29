package ru.miro.analytics_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.miro.analytics_service.dto.HealthDataDTO;
import ru.miro.analytics_service.service.UsersStatisticsService;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ObjectMapper objectMapper;
    private final UsersStatisticsService usersStatisticsService;

    @KafkaListener(topics = "healthData", groupId = "groupId")
    void healthDataListener(String data) throws JsonProcessingException {
        JsonNode jsonNodeHealthData = objectMapper.readTree(data);
        HealthDataDTO healthDataDTO = HealthDataDTO.builder()
                .userId(jsonNodeHealthData.get("userId").asLong())
                .steps(jsonNodeHealthData.get("steps").asInt())
                .calories(jsonNodeHealthData.get("calories").asInt())
                .sleepHours((byte) jsonNodeHealthData.get("sleepHours").asInt())
                .heartRate(jsonNodeHealthData.get("heartRate").asInt())
                .build();

        usersStatisticsService.save(healthDataDTO);
        System.out.println("Listener received: healthDataDTO - with userId=" + healthDataDTO.getUserId() + " 🎉");
    }
}
