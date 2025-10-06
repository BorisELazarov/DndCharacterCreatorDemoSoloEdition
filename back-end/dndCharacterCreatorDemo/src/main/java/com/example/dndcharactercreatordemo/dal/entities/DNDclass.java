package com.example.dndcharactercreatordemo.dal.entities;

import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Classes")
public class DNDclass extends BaseEntity{
    @Column(name="name", unique = true, length = 40)
    @NotBlank(message = "Name must not be empty")
    private String name;
    @Column(name="description", nullable = false, length = 65535)
    @NotBlank(message = "Description must not be empty")
    private String description;
    @Column(name="hit_dice", nullable = false)
    @NotNull(message = "The hit dice must not be empty")
    @Enumerated(EnumType.STRING)
//    @Min(value = 0, message = "The hit dice must be at least 6")
//    @Max(value = 3, message = "The hit dice must be at max 12")
    private HitDiceEnum hitDice;
    @ManyToMany
    @JoinTable(
            name = "Proficiency_Classes",
            joinColumns = { @JoinColumn(name = "class_id") },
            inverseJoinColumns = { @JoinColumn(name = "proficiency_id") }
    )
    private Set<Proficiency> proficiencies;
    @OneToMany(mappedBy = "id.dndClass")
    private List<ClassSpell> classSpells;

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

    @Override
    public boolean equals(Object o) {
        if (super.equals(o))
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNDclass dnDclass = (DNDclass) o;
        return Objects.equals(name, dnDclass.name) && Objects.equals(description, dnDclass.description)
                && Objects.equals(proficiencies, dnDclass.proficiencies)
                && isDeleted && dnDclass.getIsDeleted();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description,
                hitDice, proficiencies, classSpells,
                isDeleted);
    }
}
