package com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes;

import com.example.dnd_character_creator_solo_edition.bll.dtos.features.FeatureDTO;
import jakarta.validation.constraints.NotNull;

public record ClassFeatureDTO(
        @NotNull(message = "Class feature dto must have feature to it")
        FeatureDTO feature,
        @NotNull(message = "Class feature dto must have level")
        @NotNull byte level
) {
}
