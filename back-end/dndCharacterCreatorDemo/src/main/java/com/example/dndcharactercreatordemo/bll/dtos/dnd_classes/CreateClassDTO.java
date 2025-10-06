package com.example.dndcharactercreatordemo.bll.dtos.dnd_classes;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;

public record CreateClassDTO(Boolean isDeleted,
                             String name, String description,
                             HitDiceEnum hitDice,
                             List<SaveProficiencyDTO> proficiencies) {
}
