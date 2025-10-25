package com.example.dnd_character_creator_solo_edition.bll.services.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.features.FeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.features.SearchFeatureDTO;

import java.util.List;

public interface FeatureService {
    List<FeatureDTO> getAll(SearchFeatureDTO search, boolean deleted);

    FeatureDTO getFeature(Long id);

    FeatureDTO addFeature(FeatureDTO featureDTO);

    FeatureDTO updateFeature(FeatureDTO featureDTO);

    void restoreFeature(Long id);

    void softDeleteFeature(Long id);

    void hardDeleteFeature(Long id);
}
