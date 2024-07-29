package ru.miro.analytics_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.miro.analytics_service.dto.HealthDataDTO;
import ru.miro.analytics_service.model.UserStatistics;
import ru.miro.analytics_service.service.UsersStatisticsService;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final UsersStatisticsService usersStatisticsService;

    @GetMapping()
    public List<UserStatistics> getAllUserStatistics() {
        return usersStatisticsService.findAll();
    }

    @GetMapping("/{id}")
    public UserStatistics getUserStatistics(@PathVariable("id") long id) {
        return usersStatisticsService.findByUserId(id);
    }

    @PostMapping("/add")
    public HttpStatus addUserStatistics(@RequestBody HealthDataDTO healthDataDTO) {
        usersStatisticsService.save(healthDataDTO);
        return HttpStatus.CREATED;
    }

}
