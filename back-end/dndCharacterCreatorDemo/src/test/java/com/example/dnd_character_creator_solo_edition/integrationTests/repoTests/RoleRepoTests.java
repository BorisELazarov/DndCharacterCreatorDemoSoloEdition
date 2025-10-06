package com.example.dnd_character_creator_solo_edition.integrationTests.repoTests;

import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepoTests {
    private final RoleRepo repo;

    @Autowired
    public RoleRepoTests(RoleRepo repo) {
        this.repo = repo;
    }

    @BeforeAll
    static void seedRoles(@Autowired RoleRepo seedRepo) {
        List<Role> roles = new ArrayList<>();
        roles.add(getRole("user"));
        roles.add(getRole("data manager"));
        roles.add(getRole("admin"));

        seedRepo.saveAll(roles);
    }

    static Role getRole(String title){
        Role role=new Role();
        role.setTitle(title);
        return role;
    }

    @Test
    void findByTitleAreEquals() {
        Optional<Role> role = repo.findAll().stream().findFirst();
        assertTrue(role.isPresent());
        Optional<Role> foundRole = repo.findByTitle(role.get().getTitle());
        assertTrue(foundRole.isPresent());
        assertEquals(role, foundRole);
    }

    @AfterAll
    static void rootRoles(@Autowired RoleRepo rootRepo ){
        rootRepo.deleteAll();
    }
}
