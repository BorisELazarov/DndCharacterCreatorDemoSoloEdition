package com.example.dndcharactercreatordemo.bll.dtos.spells;

import java.util.Optional;

public record SaveSpellDTO(Optional<Long> id, Boolean isDeleted,
                           String name, int level,
                           String castingTime, int castingRange,
                           String target, String components,
                           int duration, String description){
}
