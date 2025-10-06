package com.example.dndcharactercreatordemo.bll.dtos.dnd_classes;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;

import java.util.List;
import java.util.Optional;

public record SaveClassDTO(Optional<Long> id, Boolean isDeleted,
                           String name, String description,
                           HitDiceEnum hitDice,
                           List<SaveProficiencyDTO> proficiencies){
//    public SaveClassDTO {
//        if (isDeleted==null)
//        {
//            isDeleted=false;
//        }
//    }
}
