package com.example.dnd_character_creator_solo_edition.dal.entities;

import com.example.dnd_character_creator_solo_edition.dal.entities.composite_ids.CharacterSpellPairId;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "character_spells")
public class CharacterSpell implements Serializable {
    @EmbeddedId
    private CharacterSpellPairId id;

    public CharacterSpellPairId getId() {
        return id;
    }

    public void setId(CharacterSpellPairId id) {
        this.id = id;
    }
}
