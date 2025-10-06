package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficiencyRepo extends JpaRepository<Proficiency, Long> {
    public Proficiency findByName(String name);
}
