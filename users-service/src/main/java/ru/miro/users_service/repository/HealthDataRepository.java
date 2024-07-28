package ru.miro.users_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.miro.users_service.dto.UserDTO;
import ru.miro.users_service.model.HealthData;

import java.util.List;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
    List<HealthData> getAllByUserId(long id);
}
