package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.features.FeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Feature;

public interface FeatureMapper extends ISingleParameterMapper<FeatureDTO, Feature> {
}
