package ru.miro.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.miro.user_service.dto.HealthDataDTO;
import ru.miro.user_service.exception.HealthDataNotFoundException;
import ru.miro.user_service.mapper.HealthDataMapper;
import ru.miro.user_service.model.HealthData;
import ru.miro.user_service.model.User;
import ru.miro.user_service.repository.HealthDataRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HealthDataService {

    private final HealthDataRepository healthDataRepository;
    private final UsersService usersService;

    public List<HealthDataDTO> findAll() {
        return healthDataRepository.findAll().stream().map(healthDataMapper::toDTO).collect(Collectors.toList());
    }
    private final HealthDataMapper healthDataMapper;

    public HealthDataDTO findOne(long id) {
        return healthDataRepository.findById(id)
                .map(healthDataMapper::toDTO)
                .orElseThrow(() -> new HealthDataNotFoundException(id));
    }

    @Transactional
    public void save(HealthDataDTO healthDataDTO) {
        User user = usersService.findOne(healthDataDTO.getUserId());

        HealthData healthData = healthDataMapper.toEntity(healthDataDTO);
        healthData.setUser(user);
        enrichCreatedHealthData(healthData);

        healthDataRepository.save(healthData);
    }

    @Transactional
    public void update(long id, HealthDataDTO healthDataDTO) {
        Optional<HealthData> healthData = healthDataRepository.findById(id);

        if(healthData.isEmpty()) {
            throw new HealthDataNotFoundException(id);
        }

        HealthData updatedHealthData = healthDataMapper.updateFromDto(healthDataDTO, healthData.get());
        enrichUpdatedHealthData(updatedHealthData);

        healthDataRepository.save(updatedHealthData);
    }

    @Transactional
    public void delete(long id) {
        if(healthDataRepository.findById(id).isEmpty()) {
            throw new HealthDataNotFoundException(id);
        }

        healthDataRepository.deleteById(id);
    }

    private void enrichCreatedHealthData(HealthData healthData) {
        healthData.setCreatedAt(System.currentTimeMillis());
        healthData.setUpdatedAt(System.currentTimeMillis());
    }

    private void enrichUpdatedHealthData(HealthData healthData) {
        healthData.setUpdatedAt(System.currentTimeMillis());
    }
}