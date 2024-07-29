package ru.miro.analytics_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "healthData", groupId = "groupId")
    void healthDataListener(String data) {
        System.out.println("Listener received: " + data + " 🎉");
    }
}
