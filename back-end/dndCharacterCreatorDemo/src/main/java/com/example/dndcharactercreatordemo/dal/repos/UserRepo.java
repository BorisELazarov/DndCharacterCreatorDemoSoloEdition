package com.example.dndcharactercreatordemo.dal.repos;

import com.example.dndcharactercreatordemo.dal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
