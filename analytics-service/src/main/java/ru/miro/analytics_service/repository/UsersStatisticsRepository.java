package ru.miro.analytics_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.miro.analytics_service.model.UserStatistics;

import java.util.Optional;

@Repository
public interface UsersStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    Optional<UserStatistics> findByUserId(long id);
}
