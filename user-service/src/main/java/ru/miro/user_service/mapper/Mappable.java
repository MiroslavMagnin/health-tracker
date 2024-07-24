package ru.miro.user_service.mapper;

public interface Mappable<E, D> {

    E toEntity(D dto);

    D toDTO(E entity);

}
