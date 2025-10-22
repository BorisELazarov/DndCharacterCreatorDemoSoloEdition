package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="proficiencies")
public class Proficiency extends BaseEntity implements Serializable {
    @Column(name="name", nullable = false, length = 50)
    private String name;
    @ManyToOne
    @JoinColumn(name="type_id", nullable = false)
    private ProfType type;

    @ManyToOne
    @JoinColumn(name="subtype_id")
    private ProfSubtype subtype;

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

    public ProfSubtype getSubtype() {
        return subtype;
    }

    public void setSubtype(ProfSubtype subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proficiency that)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(type, that.type)) return false;
        return Objects.equals(subtype, that.subtype);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (subtype != null ? subtype.hashCode() : 0);
        return result;
    }
}
