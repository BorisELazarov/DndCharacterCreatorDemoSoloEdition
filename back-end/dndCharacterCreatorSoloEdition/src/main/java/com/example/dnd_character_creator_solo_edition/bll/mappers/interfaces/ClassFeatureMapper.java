package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassFeatureDTO;
import com.example.dnd_character_creator_solo_edition.dal.entities.ClassFeature;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;

import java.util.List;

public interface ClassFeatureMapper {

    ClassFeature fromDto(ClassFeatureDTO dto, DNDclass dndClass);

    ClassFeatureDTO toDto(ClassFeature entity);

    List<ClassFeature> fromDTOs(List<ClassFeatureDTO> dtos, DNDclass dndClass);

    List<ClassFeatureDTO> toDTOs(List<ClassFeature> entities);
}
