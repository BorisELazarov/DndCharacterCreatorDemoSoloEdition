package com.example.dnd_character_creator_solo_edition.dal.entities;

import com.example.dnd_character_creator_solo_edition.enums.ProfSubType;
import com.example.dnd_character_creator_solo_edition.enums.ProfType;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="proficiencies")
public class Proficiency extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name =" type", nullable = false)
    private ProfType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "subtype", nullable = false)
    private ProfSubType profSubType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfType getType() {
        return type;
    }

    public void setType(ProfType type) {
        this.type = type;
    }

    public ProfSubType getSubtype() {
        return profSubType;
    }

    public void setSubtype(ProfSubType profSubType) {
        this.profSubType = profSubType;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass()!=this.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy oProxy? oProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy? thisProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Proficiency that = (Proficiency) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
