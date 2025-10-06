package com.example.dndcharactercreatordemo.bll.mappers;

import java.util.List;

public interface IMapper<C, S, R, K> {
    K fromCreateDto(C dto);
    K fromSaveDto(S dto);
    R toDto(K entity);
    List<K> fromCreateDTOs(List<C> dtos);
    List<K> fromSaveDTOs(List<S> dtos);
    List<R> toDTOs(List<K> entities);
}
