package com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;

import java.util.List;

public record CreateClassDTO(Boolean isDeleted,
                             String name, String description,
                             HitDiceEnum hitDice,
                             List<SaveProficiencyDTO> proficiencies) {
}
