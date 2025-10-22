package com.example.dnd_character_creator_solo_edition.bll.services.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.SearchCharacterDTO;

import java.util.List;

public interface CharacterService {
    List<CharacterDTO> getCharacters(boolean isDeleted, SearchCharacterDTO searchCharacterDTO);
    CharacterDTO addCharacter(CharacterDTO dto);
    void restoreCharacter(Long id);
    CharacterDTO editCharacter(CharacterDTO dto);
    void softDeleteCharacter(Long id);
    CharacterDTO getCharacterById(Long id);
    void hardDeleteCharacter(Long id);
}
