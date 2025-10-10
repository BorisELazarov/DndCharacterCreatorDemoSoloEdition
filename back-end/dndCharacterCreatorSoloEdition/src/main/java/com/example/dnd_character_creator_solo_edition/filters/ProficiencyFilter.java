package com.example.dnd_character_creator_solo_edition.filters;

import com.example.dnd_character_creator_solo_edition.enums.ProfSubType;
import com.example.dnd_character_creator_solo_edition.enums.ProfType;

import java.util.Optional;

/**
 * @author boriselazarov@gmail
 */
public record ProficiencyFilter(String name, Optional<ProfType> type, Optional<ProfSubType> subType) {
}
