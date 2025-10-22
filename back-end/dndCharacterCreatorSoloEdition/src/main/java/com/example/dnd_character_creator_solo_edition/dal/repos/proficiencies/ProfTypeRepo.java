package com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies;

import com.example.dnd_character_creator_solo_edition.dal.entities.ProfType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfTypeRepo extends JpaRepository<ProfType, Long> {
    @Query("Select pt from ProfType pt where pt.name=:name and pt.isDeleted=false")
    Optional<ProfType> findByName(String name);

    @Query("Select pt.name from ProfType pt where pt.isDeleted = false")
    List<String> getTypeNames();
}
