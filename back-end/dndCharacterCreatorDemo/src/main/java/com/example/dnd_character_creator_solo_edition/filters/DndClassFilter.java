package com.example.dnd_character_creator_solo_edition.filters;

import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;

import java.util.Optional;

/**
 * @author boriselazarov@gmail
 */
public record DndClassFilter(String name, Optional<HitDiceEnum> hitDice) {
}
