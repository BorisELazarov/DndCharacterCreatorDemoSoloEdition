package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.parents.ISingleParameterMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Spell;

public interface SpellMapper extends ISingleParameterMapper<SpellDTO, Spell> {
}
