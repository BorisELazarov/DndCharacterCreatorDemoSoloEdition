package com.example.dnd_character_creator_solo_edition.dal.repos;

import com.example.dnd_character_creator_solo_edition.dal.entities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpellRepo extends JpaRepository<Spell,Long> {
    @Query("Select s from Spell s where s.isDeleted=:isDeleted")
    List<Spell> findAll(boolean isDeleted);
    @Query("Select s from Spell s where s.name=:name and s.isDeleted=false")
    Optional<Spell> findByName(String name);
}
