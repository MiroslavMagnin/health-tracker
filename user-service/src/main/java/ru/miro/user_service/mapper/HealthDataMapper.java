package ru.miro.user_service.mapper;

import org.mapstruct.*;
import ru.miro.user_service.dto.HealthDataDTO;
import ru.miro.user_service.model.HealthData;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HealthDataMapper extends Mappable<HealthData, HealthDataDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    HealthData updateFromDto(HealthDataDTO dto, @MappingTarget HealthData entity);

    @Override
    @Mapping(target = "userId", source = "user.id")
    HealthDataDTO toDTO(HealthData entity);
}
