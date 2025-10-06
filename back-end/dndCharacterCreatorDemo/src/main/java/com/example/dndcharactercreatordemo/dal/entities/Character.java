package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "characters")
public class Character extends BaseEntity{
    @Column(name="name", nullable = false, length = 50)
    @NotBlank(message = "Name is mandatory")
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @NotNull(message = "User is mandatory")
    private User user;
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    @NotNull(message = "Class is mandatory")
    private DNDclass dndClass;
    @Column(name="level", nullable = false)
    @Min(value = 1, message = "Level must be above 0")
    @Max(value = 20, message = "The maximum level is 20")
    private byte level;
    @Column(name = "base_str", nullable = false)
    @Min(value = 0, message = "The strength stat must be at least 0")
    private byte baseStr=10;
    @Column(name = "base_dex", nullable = false)
    @Min(value = 0, message = "The dexterity stat must be at least 0")
    private byte baseDex=10;
    @Column(name = "base_con", nullable = false)
    @Min(value = 0, message = "The constitution stat must be at least 0")
    private byte baseCon=10;
    @Column(name = "base_int", nullable = false)
    @Min(value = 0, message = "The intelligence stat must be at least 0")
    private byte baseInt=10;
    @Column(name = "base_wis", nullable = false)
    @Min(value = 0, message = "The wisdom stat must be at least 0")
    private byte baseWis=10;
    @Column(name = "base_cha", nullable = false)
    @Min(value = 0, message = "The charisma stat must be at least 0")
    private byte baseCha=10;

    @OneToMany(mappedBy = "id.character")
    private List<ProficiencyCharacter> proficiencyCharacters;


    @OneToMany(mappedBy = "id.character")
    private List<CharacterSpell> characterSpells;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DNDclass getDNDclassId() {
        return dndClass;
    }

    public void setDNDclassId(DNDclass dndClass) {
        this.dndClass = dndClass;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public byte getBaseStr() {
        return baseStr;
    }

    public void setBaseStr(byte baseStr) {
        this.baseStr = baseStr;
    }

    public byte getBaseDex() {
        return baseDex;
    }

    public void setBaseDex(byte baseDex) {
        this.baseDex = baseDex;
    }

    public byte getBaseCon() {
        return baseCon;
    }

    public void setBaseCon(byte baseCon) {
        this.baseCon = baseCon;
    }

    public byte getBaseInt() {
        return baseInt;
    }

    public void setBaseInt(byte baseInt) {
        this.baseInt = baseInt;
    }

    public byte getBaseWis() {
        return baseWis;
    }

    public void setBaseWis(byte baseWis) {
        this.baseWis = baseWis;
    }

    public byte getBaseCha() {
        return baseCha;
    }

    public void setBaseCha(byte baseCha) {
        this.baseCha = baseCha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (super.equals(o))
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name)
                && isDeleted && character.isDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user, dndClass,
                level, baseStr, baseDex, baseCon,
                baseInt, baseWis, baseCha,
                proficiencyCharacters, characterSpells,
                isDeleted);
    }
}