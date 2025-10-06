package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Proficiency_Characters")
public class ProficiencyCharacter{
    @EmbeddedId
    private ProficiencyCharacterPairId id;
    @Column(name="expertise", nullable = false)
    private boolean expertise;
}
