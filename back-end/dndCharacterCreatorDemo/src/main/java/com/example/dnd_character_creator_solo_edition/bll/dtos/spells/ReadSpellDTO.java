package com.example.dnd_character_creator_solo_edition.bll.dtos.spells;

public record ReadSpellDTO(Long id, boolean isDeleted, String name, int level, String castingTime, int castingRange,
                           String target, String components, int duration, String description) {
}
