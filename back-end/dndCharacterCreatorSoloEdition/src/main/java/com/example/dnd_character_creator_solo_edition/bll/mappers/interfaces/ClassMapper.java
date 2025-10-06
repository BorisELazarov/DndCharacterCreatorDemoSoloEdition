package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;

public interface ClassMapper extends ISingleParameterMapper<ClassDTO, DNDclass> {
}
