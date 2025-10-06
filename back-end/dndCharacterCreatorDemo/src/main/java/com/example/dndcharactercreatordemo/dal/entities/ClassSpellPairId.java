package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ClassSpellPairId {
    @ManyToOne
    @JoinColumn(name="spell_id")
    @NotNull(message = "Spell must not be null")
    private Spell spell;
    @ManyToOne
    @JoinColumn(name="class_id")
    @NotNull(message = "DND class must not be null")
    private DNDclass dndClass;
}
