package com.example.dnd_character_creator_solo_edition.bll.dtos.features;

import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.filters.FeatureFilter;

public record SearchFeatureDTO(FeatureFilter filter, Sort sort) {
}
