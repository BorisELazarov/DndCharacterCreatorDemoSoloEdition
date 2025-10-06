package com.example.dnd_character_creator_solo_edition.dal.repos;

import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query("Select r from Role r where r.title=:title")
    Optional<Role> findByTitle(String title);
}