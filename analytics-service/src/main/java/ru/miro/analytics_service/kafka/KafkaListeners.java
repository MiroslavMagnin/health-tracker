package ru.miro.analytics_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.miro.analytics_service.dto.HealthDataDTO;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "healthData", groupId = "groupId")
    void healthDataListener(String data) throws JsonProcessingException {
        JsonNode jsonNodeHealthData = objectMapper.readTree(data);
        System.out.println("Listener received: " + jsonNodeHealthData.get("userId").asInt() + " 🎉");
    }
}
