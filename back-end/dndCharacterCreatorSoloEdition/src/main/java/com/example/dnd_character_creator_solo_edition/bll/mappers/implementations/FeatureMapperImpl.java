package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.features.FeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.FeatureMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Feature;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FeatureMapperImpl implements FeatureMapper {
    @Override
    public Feature fromDto(FeatureDTO dto) {
        Feature feature = new Feature();
        if (dto.id().isPresent()) {
            feature.setId(dto.id().get());
        }
        feature.setName(dto.name());
        feature.setDescription(dto.description());
        return feature;
    }

    @Override
    public FeatureDTO toDto(Feature entity) {
        return new FeatureDTO(Optional.of(entity.getId()), entity.getName(), entity.getDescription());
    }

    @Override
    public List<Feature> fromDTOs(List<FeatureDTO> dtos) {
        return dtos.stream().map(this::fromDto).toList();
    }

    @Override
    public List<FeatureDTO> toDTOs(List<Feature> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
