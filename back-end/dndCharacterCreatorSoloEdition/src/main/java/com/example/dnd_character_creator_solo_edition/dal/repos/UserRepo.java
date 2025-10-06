package com.example.dnd_character_creator_solo_edition.dal.repos;

import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("Select u from User u where u.username=:username and u.isDeleted=false")
    Optional<User> findByUsername(String username);

    @Query("Select u from User u where u.isDeleted=false")
    List<User> findAllUndeleted();

    @Query("Select u from User u where u.isDeleted=true")
    List<User> findAllSoftDeleted();

    @Query("Select u from User u where u.username=:username and u.password=:password and u.isDeleted=true")
    Optional<User> findDeletedByUsernameAndPassword(String username, String password);

    @Query("Select u from User u where u.email=:email")
    Optional<User> findByEmail(String email);
}
