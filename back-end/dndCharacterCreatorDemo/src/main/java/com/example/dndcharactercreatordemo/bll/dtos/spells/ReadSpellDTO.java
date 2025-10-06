package com.example.dndcharactercreatordemo.bll.dtos.spells;

public record ReadSpellDTO(Long id, boolean isDeleted, String name, int level, String castingTime, int castingRange,
                           String target, String components, int duration, String description) {
}
