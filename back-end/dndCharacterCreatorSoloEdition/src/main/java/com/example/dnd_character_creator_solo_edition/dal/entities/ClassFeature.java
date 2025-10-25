package com.example.dnd_character_creator_solo_edition.dal.entities;

import com.example.dnd_character_creator_solo_edition.dal.entities.composite_ids.ClassFeaturePairId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "class_features")
public class ClassFeature {

    @EmbeddedId
    private ClassFeaturePairId id;

    @Column(name = "level")
    private byte level = 1;

    public ClassFeaturePairId getId() {
        return id;
    }

    public void setId(ClassFeaturePairId id) {
        this.id = id;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }
}
