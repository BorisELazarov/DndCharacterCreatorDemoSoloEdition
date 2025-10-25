package com.example.dnd_character_creator_solo_edition.dal.repos.features;

import com.example.dnd_character_creator_solo_edition.dal.entities.ClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassFeatureRepo extends JpaRepository<ClassFeature, Long> {

    @Query("Select cf from ClassFeature cf where cf.id.dndClass.id = :classId")
    List<ClassFeature> findByClassId(Long classId);
}
