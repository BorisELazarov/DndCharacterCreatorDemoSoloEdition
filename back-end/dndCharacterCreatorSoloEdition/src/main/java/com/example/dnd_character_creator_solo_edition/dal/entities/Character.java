package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "characters")
public class Character extends BaseEntity implements Serializable {
    @Column(name="name", nullable = false, length = 50)
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private DNDclass dndClass;
    @Column(name="level", nullable = false)
    private byte level;
    @Column(name = "base_str", nullable = false)
    private byte baseStr=10;
    @Column(name = "base_dex", nullable = false)
    private byte baseDex=10;
    @Column(name = "base_con", nullable = false)
    private byte baseCon=10;
    @Column(name = "base_int", nullable = false)
    private byte baseInt=10;
    @Column(name = "base_wis", nullable = false)
    private byte baseWis=10;
    @Column(name = "base_cha", nullable = false)
    private byte baseCha=10;

    @OneToMany(mappedBy = "id.character", cascade= CascadeType.ALL,
    orphanRemoval = true)
    private List<ProficiencyCharacter> proficiencyCharacters;


    @OneToMany(mappedBy = "id.character", cascade= CascadeType.ALL,
            orphanRemoval = true)
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

    public DNDclass getDNDclass() {
        return dndClass;
    }

    public void setDNDclass(DNDclass dndClass) {
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

    public List<ProficiencyCharacter> getProficiencyCharacters() {
        return proficiencyCharacters;
    }

    public void setProficiencyCharacters(List<ProficiencyCharacter> proficiencyCharacters) {
        this.proficiencyCharacters = proficiencyCharacters;
    }

    public List<CharacterSpell> getCharacterSpells() {
        return characterSpells;
    }

    public void setCharacterSpells(List<CharacterSpell> characterSpells) {
        this.characterSpells = characterSpells;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass()!=this.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy oProxy? oProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy ? thisProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Character character = (Character) o;
        return getId() != null && Objects.equals(getId(), character.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}