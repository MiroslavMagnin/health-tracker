package ru.miro.users_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import ru.miro.users_service.dto.HealthDataDTO;

import java.io.ObjectOutputStream;
import java.util.Map;

public class CustomSerializer implements Serializer<HealthDataDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, HealthDataDTO data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.close();
            byte[] b = baos.toByteArray();
            return b;
        } catch (Exception e) {
            throw new SerializationException("Error when serializing HealthDataDTO to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
