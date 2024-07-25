package ru.miro.users_service.mapper;

public interface Mappable<E, D> {

    E toEntity(D dto);

    D toDTO(E entity);

}
