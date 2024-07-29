package ru.miro.analytics_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import ru.miro.analytics_service.dto.HealthDataDTO;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

@Slf4j
public class CustomDeserializer implements Deserializer<HealthDataDTO> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public HealthDataDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (HealthDataDTO) ois.readObject();
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to HealthDataDTO", e);
        }
    }

    @Override
    public void close() {
    }
}

