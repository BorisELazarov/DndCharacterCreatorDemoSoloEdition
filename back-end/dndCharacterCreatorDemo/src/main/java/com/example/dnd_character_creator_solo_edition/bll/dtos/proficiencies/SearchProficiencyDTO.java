package com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies;

import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.filters.ProficiencyFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchProficiencyDTO(ProficiencyFilter filter, Sort sort) {
}
