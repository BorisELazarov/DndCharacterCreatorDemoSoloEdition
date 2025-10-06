package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "spells")
public class Spell extends BaseEntity implements Serializable {
    @Column(name="name", nullable = false, length = 50)
    private String name;
    @Column(name="level", nullable = false)
    private int level;
    @Column(name="casting_time", nullable = false, length = 50)
    private String castingTime;
    @Column(name="casting_range")
    private int castingRange;
    @Column(name="target", length = 50)
    private String target;
    @Column(name="components", nullable = false)
    private String components;
    @Min(0)
    @Column(name="duration", nullable = false)
    private int duration;
    @Column(name="description", nullable = false, length = 65535)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public int getCastingRange() {
        return castingRange;
    }

    public void setCastingRange(int castingRange) {
        this.castingRange = castingRange;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass()!=this.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy oProxy? oProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy? thisProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Spell spell = (Spell) o;
        return getId() != null && Objects.equals(getId(), spell.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
