package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterMapper {
    Character fromDto(CharacterDTO characterDTO);
    CharacterDTO toDto(Character character);
    List<CharacterDTO> toDTOs(List<Character> characters);
}
