package com.example.dnd_character_creator_solo_edition.bll.dtos.features;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record FeatureDTO(Optional<Long> id,

        @NotNull
        @Size(min = 3, max = 50)
        String name,

        @NotNull
        @Size(min = 10)
        String description
) {
}
