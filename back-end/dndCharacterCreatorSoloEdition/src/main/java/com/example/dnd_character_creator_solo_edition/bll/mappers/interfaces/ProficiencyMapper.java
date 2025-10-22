package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProfType;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;

import java.util.List;

public interface ProficiencyMapper {
    Proficiency fromDto(ProficiencyDTO dto, ProfType type);
    ProficiencyDTO toDto(Proficiency entity);
    List<Proficiency> fromDTOs(List<ProficiencyDTO> dtos);

    List<ProficiencyDTO> toDTOs(List<Proficiency> entities);
}
