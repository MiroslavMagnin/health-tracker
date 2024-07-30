package ru.miro.users_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.miro.users_service.dto.HealthDataDTO;
import ru.miro.users_service.exception.HealthDataNotCreatedException;
import ru.miro.users_service.model.HealthData;
import ru.miro.users_service.service.HealthDataService;

import java.util.List;

@RestController
@RequestMapping("/users/health-data")
@RequiredArgsConstructor
public class HealthDataController {

    private final HealthDataService healthDataService;
    private final KafkaTemplate<String, HealthDataDTO> kafkaTemplate;

    @GetMapping()
    public List<HealthData> getHealthData() {
        return healthDataService.findAll();
    }

    @GetMapping("/{id}")
    public HealthData getHealthData(@PathVariable("id") long id) {
        return healthDataService.findOne(id);
    }

    @GetMapping("/getByUserId/{id}")
    public List<HealthData> getHealthDataByUserId(@PathVariable("id") long id) {
        return healthDataService.getByUserId(id);
    }

    @PostMapping("/add")
    public HttpStatus addHealthData(@RequestBody @Valid HealthDataDTO healthDataDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");

                throw new HealthDataNotCreatedException(errorMessage.toString());
            }
        }

        healthDataService.save(healthDataDTO);
        kafkaTemplate.send("healthData", healthDataDTO);
        return HttpStatus.CREATED;
    }

    @PatchMapping("/{id}/update")
    public HttpStatus updateHealthData(@PathVariable("id") long id,
                                       @RequestBody HealthDataDTO healthDataDTO) {
        healthDataService.update(id, healthDataDTO);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteHealthData(@PathVariable("id") long id) {
        healthDataService.delete(id);
        return HttpStatus.OK;
    }

}
