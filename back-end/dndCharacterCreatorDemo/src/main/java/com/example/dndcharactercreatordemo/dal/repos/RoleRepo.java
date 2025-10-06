package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    public Role findByTitle(String title);
}