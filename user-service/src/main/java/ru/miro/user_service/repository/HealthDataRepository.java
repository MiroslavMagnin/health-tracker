package ru.miro.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.miro.user_service.model.HealthData;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Long> {

}
