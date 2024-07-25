package ru.miro.users_service.mapper;

import org.mapstruct.*;
import ru.miro.users_service.dto.UserDTO;
import ru.miro.users_service.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends Mappable<User, UserDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateFromDto(UserDTO dto, @MappingTarget User entity);
}
