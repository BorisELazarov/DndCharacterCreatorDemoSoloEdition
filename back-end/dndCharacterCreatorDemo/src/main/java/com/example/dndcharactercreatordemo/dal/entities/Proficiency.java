package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name="proficiencies")
public class Proficiency extends BaseEntity {
    @Column(name="name", nullable = false, length = 50)
    @NotBlank(message = "Name must not be empty")
    private String name;
    @Column(name="type", nullable = false, length = 50)
    @NotBlank(message = "Type must not be empty")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o))
            return true;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proficiency proficiency = (Proficiency) o;
        return Objects.equals(name, proficiency.name) && Objects.equals(type, proficiency.type)
                && isDeleted && proficiency.getIsDeleted();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isDeleted);
    }
}
