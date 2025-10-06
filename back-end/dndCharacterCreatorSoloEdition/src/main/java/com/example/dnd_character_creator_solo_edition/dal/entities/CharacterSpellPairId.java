package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class CharacterSpellPairId implements Serializable {
    @ManyToOne
    @JoinColumn(name="spell_id", nullable = false)
    private Spell spell;

    @ManyToOne
    @JoinColumn(name="character_id", nullable = false)
    private Character character;

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
