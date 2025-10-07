package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.parents;

import java.util.List;


public interface ISingleParameterMapper<D, E> {
    E fromDto(D dto);
    D toDto(E entity);
    List<E> fromDTOs(List<D> dtos);

    List<D> toDTOs(List<E> entities);
}
