package com.example.dnd_character_creator_solo_edition.dal.repos;

import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProficiencyRepo extends JpaRepository<Proficiency, Long> {
    @Query("Select p from Proficiency p where p.isDeleted=:isDeleted")
    List<Proficiency> findAll(boolean isDeleted);
    @Query("Select p from Proficiency p where p.name=:name and p.isDeleted=false")
    Optional<Proficiency> findByName(String name);
}
