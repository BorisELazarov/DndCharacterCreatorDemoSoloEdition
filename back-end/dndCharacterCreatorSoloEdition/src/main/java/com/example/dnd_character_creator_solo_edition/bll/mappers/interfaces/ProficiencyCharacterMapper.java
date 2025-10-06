package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProficiencyCharacter;

import java.util.List;
import java.util.Set;

public interface ProficiencyCharacterMapper{
    ProficiencyCharacter fromDto(ProficiencyCharacterDTO dto, Character character);
    ProficiencyCharacterDTO toDto(ProficiencyCharacter entity);
    List<ProficiencyCharacter> fromDTOs(List<ProficiencyCharacterDTO> dtos, Character character);
    Set<ProficiencyCharacterDTO> toDTOs(List<ProficiencyCharacter> entities);
}
