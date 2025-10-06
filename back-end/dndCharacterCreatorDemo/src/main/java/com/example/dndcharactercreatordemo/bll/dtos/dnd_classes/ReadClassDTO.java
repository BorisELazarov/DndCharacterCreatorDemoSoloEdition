package com.example.dndcharactercreatordemo.bll.dtos.dnd_classes;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;

public record ReadClassDTO(Long id, Boolean isDeleted,
                           String name, String description,
                           HitDiceEnum hitDice,
                           List<ReadProficiencyDTO> proficiencies){
}
