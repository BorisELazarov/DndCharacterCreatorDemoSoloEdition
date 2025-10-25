package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassFeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ClassFeatureMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.FeatureMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.ClassFeature;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.dal.entities.composite_ids.ClassFeaturePairId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassFeatureMapperImpl implements ClassFeatureMapper {

    private final FeatureMapper featureMapper;

    @Autowired
    public ClassFeatureMapperImpl(FeatureMapper featureMapper) {
        this.featureMapper = featureMapper;
    }

    @Override
    public ClassFeature fromDto(ClassFeatureDTO dto, DNDclass dndClass) {
        ClassFeature classFeature = new ClassFeature();
        classFeature.setLevel(dto.level());
        classFeature.setId(new ClassFeaturePairId());
        classFeature.getId().setFeature(this.featureMapper.fromDto(dto.feature()));
        classFeature.getId().setDndClass(dndClass);
        return classFeature;
    }

    @Override
    public ClassFeatureDTO toDto(ClassFeature entity) {
        return new ClassFeatureDTO(this.featureMapper.toDto(entity.getId().getFeature()), entity.getLevel());
    }

    @Override
    public List<ClassFeature> fromDTOs(List<ClassFeatureDTO> features, DNDclass dndClass) {
        return features.stream().map(feature -> this.fromDto(feature, dndClass)).toList();
    }

    @Override
    public List<ClassFeatureDTO> toDTOs(List<ClassFeature> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
