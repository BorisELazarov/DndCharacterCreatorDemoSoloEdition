package com.example.dnd_character_creator_solo_edition.dal.repos;

import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepo extends JpaRepository<Character, Long> {
    @Query("select c from Character c where c.isDeleted=:isDeleted")
    List<Character> findAll(boolean isDeleted);
    @Query("select c from Character c where c.name=:name and c.isDeleted=false")
    Optional<Character> findByName(String name);
}
