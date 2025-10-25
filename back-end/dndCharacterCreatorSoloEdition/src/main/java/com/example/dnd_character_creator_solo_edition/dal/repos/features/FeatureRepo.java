package com.example.dnd_character_creator_solo_edition.dal.repos.features;

import com.example.dnd_character_creator_solo_edition.dal.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepo extends JpaRepository<Feature, Long> {
    @Query("Select f from Feature f where f.isDeleted = :isDeleted and f.name like :name")
    List<Feature> findAll(boolean isDeleted, String name);

    @Query("Select count(f)>0 from Feature f where f.name = :name and f.isDeleted = false")
    boolean existsByName(String name);
}
