package com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies;

import com.example.dnd_character_creator_solo_edition.enums.ProfSubType;
import com.example.dnd_character_creator_solo_edition.enums.ProfType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record ProficiencyDTO(Optional<Long> id, Boolean isDeleted,
                             @Size(min = 3, max=50)
                             @NotNull(message = "Name must not be empty")
                             String name,
                             @NotNull(message = "Type must not be empty")
                             ProfType type,
                             @NotNull(message = "Subtype must not be empty")
                             ProfSubType profSubType) {
}
