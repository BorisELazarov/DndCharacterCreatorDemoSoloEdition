package com.example.dnd_character_creator_solo_edition.dal.entities.composite_ids;

import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ProficiencyCharacterPairId implements Serializable {
    @ManyToOne
    @JoinColumn(name="proficiency_id",nullable = false)
    private Proficiency proficiency;

    @ManyToOne
    @JoinColumn(name="character_id",nullable = false)
    private Character character;

    public Proficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
