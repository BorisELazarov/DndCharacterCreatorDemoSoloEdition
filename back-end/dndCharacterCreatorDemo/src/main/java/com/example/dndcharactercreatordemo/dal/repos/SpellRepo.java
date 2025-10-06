package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellRepo extends JpaRepository<Spell,Long> {
    public Spell findByName(String name);
}
