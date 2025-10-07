package com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record ProficiencyDTO(Optional<Long> id, Boolean isDeleted,
                             @Size(min = 3, max=50)
                             @NotNull(message = "Name must not be empty")
                             String name,
                             @Size(min = 3, max=50)
                             @NotNull(message = "Type must not be empty")
                             String type) {
}
