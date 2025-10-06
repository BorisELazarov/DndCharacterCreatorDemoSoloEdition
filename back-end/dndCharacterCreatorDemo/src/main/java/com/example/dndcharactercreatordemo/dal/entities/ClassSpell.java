package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "class_spells")
public class ClassSpell {
    @EmbeddedId
    private ClassSpellPairId id;
}
