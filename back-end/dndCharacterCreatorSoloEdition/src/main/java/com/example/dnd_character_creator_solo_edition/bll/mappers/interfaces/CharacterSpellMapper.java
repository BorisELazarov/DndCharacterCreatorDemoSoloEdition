package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.CharacterSpell;

import java.util.List;
import java.util.Set;

public interface CharacterSpellMapper {
    CharacterSpell fromDto(SpellDTO spellDTO, Character character);
    List<CharacterSpell> fromDTOs(Set<SpellDTO> spellDTO, Character character);
    SpellDTO toDto(CharacterSpell characterSpell);

    Set<SpellDTO> toDTOs(List<CharacterSpell> characterSpells);
}
