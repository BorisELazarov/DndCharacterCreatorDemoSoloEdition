package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class CharacterSpellPairId {
    @ManyToOne
    @JoinColumn(name="spell_id")
    @NotNull(message = "Spell must not be null")
    private Spell spell;

    @ManyToOne
    @JoinColumn(name="character_id")
    @NotNull(message = "Character must not be null")
    private Character character;
}
