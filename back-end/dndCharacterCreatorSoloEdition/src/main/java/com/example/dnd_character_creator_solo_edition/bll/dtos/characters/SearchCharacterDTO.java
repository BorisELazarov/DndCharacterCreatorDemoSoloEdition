package com.example.dnd_character_creator_solo_edition.bll.dtos.characters;

import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.filters.CharacterFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchCharacterDTO(CharacterFilter filter, Sort sort) {
}
