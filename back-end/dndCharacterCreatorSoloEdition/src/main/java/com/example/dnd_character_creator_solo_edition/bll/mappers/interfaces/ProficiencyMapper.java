package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;

public interface ProficiencyMapper extends ISingleParameterMapper<ProficiencyDTO, Proficiency> {
}
