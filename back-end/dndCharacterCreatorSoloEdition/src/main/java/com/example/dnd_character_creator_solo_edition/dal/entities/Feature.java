package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "features")
public class Feature extends BaseEntity {
    @Column(length = 50, name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature feature)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(name, feature.name)) return false;
        return Objects.equals(description, feature.description);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
