package com.example.dnd_character_creator_solo_edition.bll.dtos.characters;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import jakarta.validation.constraints.*;

import java.util.Optional;
import java.util.Set;

public record CharacterDTO(
        Optional<Long> id,
        Boolean isDeleted,
        @Size(min = 3, max = 50)
        @NotNull(message = "Name must not be empty")
        String name,
        @NotNull
        UserDTO user,
        @NotNull
        ClassDTO dndClass,
        @Min(value = 1, message = "Level must be above 0")
        @Max(value = 20, message = "The maximum level is 20")
        @NotNull
        byte level,
        @Min(value = 0, message = "The strength stat must be at least 0")
        @NotNull
        byte baseStr,
        @Min(value = 0, message = "The dexterity stat must be at least 0")
        @NotNull
        byte baseDex,
        @Min(value = 0, message = "The constitution stat must be at least 0")
        @NotNull
        byte baseCon,
        @Min(value = 0, message = "The intelligence stat must be at least 0")
        @NotNull
        byte baseInt,
        @Min(value = 0, message = "The wisdom stat must be at least 0")
        @NotNull
        byte baseWis,
        @Min(value = 0, message = "The charisma stat must be at least 0")
        @NotNull
        byte baseCha,
        @NotEmpty
        @NotNull
        Set<ProficiencyCharacterDTO> proficiencies,
        Set<SpellDTO> spells
) {
    public CharacterDTO{
        proficiencies=Set.copyOf(proficiencies);
        spells=Set.copyOf(spells);
    }
}
