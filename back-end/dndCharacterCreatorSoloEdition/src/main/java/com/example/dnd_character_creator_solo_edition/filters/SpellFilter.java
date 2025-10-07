package com.example.dnd_character_creator_solo_edition.filters;

import java.util.Optional;
/**
 * @author boriselazarov@gmail
 */
public record SpellFilter(String name, Optional<Byte> level,
                          String castingTime, Optional<Integer> range) {
}
