package com.example.dnd_character_creator_solo_edition.dal.entities.composite_ids;

import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.dal.entities.Feature;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class ClassFeaturePairId {
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private DNDclass dndClass;

    @ManyToOne
    @JoinColumn(name="feature_id", nullable = false)
    private Feature feature;

    public DNDclass getDndClass() {
        return dndClass;
    }

    public void setDndClass(DNDclass dndClass) {
        this.dndClass = dndClass;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassFeaturePairId that)) return false;

        if (!dndClass.equals(that.dndClass)) return false;
        return feature.equals(that.feature);
    }

    @Override
    public int hashCode() {
        int result = dndClass.hashCode();
        result = 31 * result + feature.hashCode();
        return result;
    }
}
