package com.example.dnd_character_creator_solo_edition.bll.dtos.spells;

import java.util.Optional;

public record SaveSpellDTO(Optional<Long> id, Boolean isDeleted,
                           String name, int level,
                           String castingTime, int castingRange,
                           String target, String components,
                           int duration, String description){
}
