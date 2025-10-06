package com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies;

import java.util.Optional;

public record SaveProficiencyDTO(Optional<Long> id, Boolean isDeleted,
                                 String name, String type) {
}
