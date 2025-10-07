package com.example.dnd_character_creator_solo_edition.bll.dtos.spells;

import jakarta.validation.constraints.*;

import java.util.Optional;

public record SpellDTO(Optional<Long> id, Boolean isDeleted,
                       @NotNull(message = "Name must not be empty")
                       @Size(min = 3, max = 50)
                       String name,
                       @Min(value = 0, message = "Level must be at least 0")
                       @Max(value = 9, message = "The maximum level is 9")
                       int level,
                       @NotNull(message = "Casting time must not be empty")
                       @Size(min = 3, max = 50)
                       String castingTime,
                       @Min(value = 0, message = "Casting range must be at least 0 feet")
                       int castingRange,
                       @NotNull(message = "Target must not be empty")
                       @Size(min = 3, max = 50)
                       String target,
                       @NotNull
                       @NotBlank(message = "The components must not be empty")
                       @Size(max=50, message = "The components must have maximum 50 characters")
                       String components,
                       @Min(value = 0, message = "Duration must be at least 0")
                       int duration,
                       @NotNull(message = "Description must not be null")
                       @Size(min = 3, max=65535)
                       String description){
}
