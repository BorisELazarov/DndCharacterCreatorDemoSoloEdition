package com.example.dnd_character_creator_solo_edition.integrationTests.repoTests;

import com.example.dnd_character_creator_solo_edition.dal.entities.BaseEntity;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProfType;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProfTypeRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProficiencyRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProficiencyRepoTests {
    @Autowired
    private final ProficiencyRepo proficiencyRepo;
    @Autowired
    private final ProfTypeRepo profTypeRepo;


    @Autowired
    public ProficiencyRepoTests(ProficiencyRepo proficiencyRepo, ProfTypeRepo profTypeRepo) {
        this.proficiencyRepo = proficiencyRepo;
        this.profTypeRepo = profTypeRepo;
    }

    @BeforeAll
    static void seedData(@Autowired ProficiencyRepo seedRepo, @Autowired ProfTypeRepo typeRepo){
        List<Proficiency> proficiencies = new ArrayList<>();
        ProfType language = addProfType("Language", typeRepo);
        proficiencies.add(seedProficiency("Common",language));
        proficiencies.add(seedProficiency("Elven",language));
        proficiencies.add(seedProficiency("Dwarvish",language));
        proficiencies.add(seedProficiency("Orcish",language));
        proficiencies.add(seedProficiency("Celestial", language));
        proficiencies.add(seedProficiency("Infernal",language));
        seedRepo.saveAll(proficiencies);
    }

    private static ProfType addProfType(String language, ProfTypeRepo typeRepo) {
        ProfType profType = new ProfType();
        profType.setName(language);
        return typeRepo.save(profType);
    }

    private static Proficiency seedProficiency(String name, ProfType type){
        Proficiency proficiency=new Proficiency();
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }


    @Test
    void findByNameAreEquals(){
        Optional<Proficiency> proficiency=proficiencyRepo.findAll().stream()
                .filter(x->!x.getIsDeleted()).findFirst();
        assertTrue(proficiency.isPresent());

        Optional<Proficiency> proficiencyByName=proficiencyRepo.findByName(proficiency.get().getName());
        assertTrue(proficiencyByName.isPresent());
        assertEquals(proficiency,proficiencyByName);
    }

    @Test
    void findAllDeletedEquals(){
        List<Proficiency> proficiencies=proficiencyRepo.findAll()
                .stream().filter(x->!x.getIsDeleted()).toList();
        List<Proficiency> deletedProficiencies=proficiencyRepo.findAll(false);
        assertEquals(proficiencies,deletedProficiencies);
    }

    @Test
    void findAllUndeletedEquals(){
        List<Proficiency> proficiencies=proficiencyRepo.findAll()
                .stream().filter(BaseEntity::getIsDeleted).toList();
        List<Proficiency> deletedProficiencies=proficiencyRepo.findAll(true);
        assertEquals(proficiencies,deletedProficiencies);
    }

    @AfterAll
    static void rootData(@Autowired ProficiencyRepo rootRepo){
        rootRepo.deleteAll();
    }
}
