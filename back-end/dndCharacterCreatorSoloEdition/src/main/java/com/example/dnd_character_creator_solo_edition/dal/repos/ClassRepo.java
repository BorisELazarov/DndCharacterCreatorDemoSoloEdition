package com.example.dnd_character_creator_solo_edition.dal.repos;

import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassRepo extends JpaRepository<DNDclass,Long> {
    @Query("Select c from DNDclass c where c.isDeleted=:isDeleted")
    List<DNDclass> findAll(boolean isDeleted);
    boolean existsByName(String name);

    @Query("Select c from DNDclass c where c.name=:name and c.isDeleted=false")
    Optional<DNDclass> findByName(String name);
}
