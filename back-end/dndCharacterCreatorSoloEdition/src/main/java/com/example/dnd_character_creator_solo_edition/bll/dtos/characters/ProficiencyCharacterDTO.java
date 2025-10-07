package com.example.dnd_character_creator_solo_edition.bll.dtos.characters;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import jakarta.validation.constraints.NotNull;

public record ProficiencyCharacterDTO(
        @NotNull
        ProficiencyDTO proficiency,
        Boolean expertise) {
}
