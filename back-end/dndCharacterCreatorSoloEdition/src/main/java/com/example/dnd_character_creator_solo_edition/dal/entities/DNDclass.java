package com.example.dnd_character_creator_solo_edition.dal.entities;

import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Classes")
public class DNDclass extends BaseEntity implements Serializable {
    @Column(name = "name", unique = true, nullable = false, length = 40)
    private String name;
    @Column(name = "description", nullable = false, length = 65535)
    private String description;
    @Column(name = "hit_dice", nullable = false)
    @Enumerated(EnumType.STRING)
    private HitDiceEnum hitDice;
    @ManyToMany
    @JoinTable(
            name = "Proficiency_Classes",
            joinColumns = {@JoinColumn(name = "class_id")},
            inverseJoinColumns = {@JoinColumn(name = "proficiency_id")}
    )
    private Set<Proficiency> proficiencies;
    @OneToMany(mappedBy = "id.dndClass")
    private List<ClassSpell> classSpells;


    @OneToMany(mappedBy = "id.dndClass", cascade = CascadeType.ALL)
    private List<ClassFeature> classFeatures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HitDiceEnum getHitDice() {
        return hitDice;
    }

    public void setHitDice(HitDiceEnum hitDice) {
        this.hitDice = hitDice;
    }

    public Set<Proficiency> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(Set<Proficiency> proficiencies) {
        this.proficiencies = proficiencies;
    }

    public List<ClassSpell> getClassSpells() {
        return classSpells;
    }

    public void setClassSpells(List<ClassSpell> classSpells) {
        this.classSpells = classSpells;
    }

    public List<ClassFeature> getClassFeatures() {
        return classFeatures;
    }

    public void setClassFeatures(List<ClassFeature> classFeatures) {
        this.classFeatures = classFeatures;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass()!= o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy oProxy ? oProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy ? thisProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DNDclass dnDclass = (DNDclass) o;
        return getId() != null && Objects.equals(getId(), dnDclass.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
