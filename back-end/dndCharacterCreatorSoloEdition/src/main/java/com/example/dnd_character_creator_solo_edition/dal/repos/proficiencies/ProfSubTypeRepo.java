package com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies;

import com.example.dnd_character_creator_solo_edition.dal.entities.ProfSubtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfSubTypeRepo extends JpaRepository<ProfSubtype, Long> {
    @Query("Select pst from ProfSubtype pst where pst.name=:name and pst.isDeleted=false")
    Optional<ProfSubtype> findByName(String name);
}
