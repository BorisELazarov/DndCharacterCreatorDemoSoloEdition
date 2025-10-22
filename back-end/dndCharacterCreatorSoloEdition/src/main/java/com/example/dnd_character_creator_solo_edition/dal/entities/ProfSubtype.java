package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "proficiency_subtypes")
public class ProfSubtype extends BaseEntity {
    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="type_id", nullable = false)
    private ProfType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
