package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<DNDclass,Long> {
    public boolean existsByName(String name);
}
