package com.example.dnd_character_creator_solo_edition.bll.dtos.spells;

import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.filters.SpellFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchSpellDTO(SpellFilter filter, Sort sort) {
}
