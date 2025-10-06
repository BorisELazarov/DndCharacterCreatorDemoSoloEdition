package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClassSpellPairId implements Serializable {
    @ManyToOne
    @JoinColumn(name="spell_id", nullable = false)
    private Spell spell;
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private DNDclass dndClass;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass()!=this.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy oProxy? oProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy? thisProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ClassSpellPairId that = (ClassSpellPairId) o;
        return spell != null && Objects.equals(spell, that.spell)
                && dndClass != null && Objects.equals(dndClass, that.dndClass);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(spell, dndClass);
    }
}
