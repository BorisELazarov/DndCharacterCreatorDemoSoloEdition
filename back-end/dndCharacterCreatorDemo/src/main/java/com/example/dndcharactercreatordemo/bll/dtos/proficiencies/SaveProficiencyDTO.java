package com.example.dndcharactercreatordemo.bll.dtos.proficiencies;

import java.util.Optional;

public record SaveProficiencyDTO(Optional<Long> id, Boolean isDeleted,
                                 String name, String type) {
}
