package com.example.dnd_character_creator_solo_edition.integrationTests.repoTests;

import com.example.dnd_character_creator_solo_edition.dal.entities.*;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.repos.CharacterRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.ClassRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.UserRepo;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CharacterRepoTests {
    private final CharacterRepo characterRepo;

    @Autowired
    public CharacterRepoTests(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    @BeforeAll
    static void seedData(@Autowired CharacterRepo seedRepo,
                         @Autowired ClassRepo classRepo,
                         @Autowired UserRepo userRepo,
                         @Autowired RoleRepo roleRepo){
        seedUser(userRepo,roleRepo);
        seedClasses(classRepo);
        List<Character> characters=new LinkedList<>();
        User user=userRepo.findAll().get(0);
        DNDclass dnDclass=classRepo.findAll().get(0);
        characters.add(getCharacter(true, "Norman", (byte)20,
                (byte)20, (byte)20, (byte) 20,
                (byte) 20, (byte) 20, (byte) 20,user,dnDclass));
        characters.add(getCharacter(false, "Morgan", (byte)2,
                (byte)12, (byte)16, (byte) 10,
                (byte) 14, (byte) 8, (byte) 14,user,dnDclass));
        characters.add(getCharacter(false, "Jordan", (byte)3,
                (byte) 14, (byte) 8, (byte) 14,
                (byte)12, (byte)16, (byte) 10,user,dnDclass));
        seedRepo.saveAll(characters);
    }

    static Character getCharacter(boolean isDeleted, String name,
                                  byte level, byte baseStr,
                                  byte baseDex, byte baseCon,
                                  byte baseInt, byte baseWis,
                                  byte baseCha, User user,
                                  DNDclass dnDclass){
        Character character=new Character();
        character.setIsDeleted(isDeleted);
        character.setName(name);
        character.setLevel(level);
        character.setBaseStr(baseStr);
        character.setBaseDex(baseDex);
        character.setBaseCon(baseCon);
        character.setBaseInt(baseInt);
        character.setBaseWis(baseWis);
        character.setBaseCha(baseCha);
        character.setUser(user);
        character.setDNDclass(dnDclass);
        return character;
    }

    static void seedUser(UserRepo userRepo, RoleRepo roleRepo){
        User user=new User();
        user.setRole(seedRole(roleRepo));
        user.setUsername("sadj[");
        user.setPassword("asfda");
        userRepo.save(user);
    }

    private static Role seedRole(RoleRepo roleRepo) {
        Role role=new Role();
        roleRepo.save(role);
        return role;
    }

    static void seedClasses(ClassRepo seedRepo) {
        seedRepo.save(getDNDclass( Optional.empty()));
    }

    static DNDclass getDNDclass(Optional<Long> id){
        DNDclass dnDclass = new DNDclass();
        id.ifPresent(dnDclass::setId);
        dnDclass.setName("Fighter");
        dnDclass.setHitDice(HitDiceEnum.D10);
        dnDclass.setIsDeleted(true);
        dnDclass.setDescription("Fights");
        return dnDclass;
    }

    @Test
    void findByNameAreEquals(){
        Optional<Character> character=characterRepo.findAll().stream().filter(x-> !x.getIsDeleted()).findFirst();
        assertTrue(character.isPresent());
        Optional<Character> foundCharacter= characterRepo.findByName(character.get().getName());
        assertTrue(foundCharacter.isPresent());
        character.ifPresent(x->assertEquals(x.getName(),foundCharacter.get().getName()));
    }

    @Test
    void findAllDeletedEquals(){
        List<Character> characters=characterRepo.findAll()
                .stream().filter(x->!x.getIsDeleted()).toList();
        List<Character> deletedCharacters=characterRepo.findAll(false);
        assertEquals(characters,deletedCharacters);
    }

    @Test
    void findAllUndeletedEquals(){
        List<Character> characters=characterRepo.findAll()
                .stream().filter(BaseEntity::getIsDeleted).toList();
        List<Character> deletedCharacters=characterRepo.findAll(true);
        assertEquals(characters,deletedCharacters);
    }

    @AfterAll
    static void rootData(@Autowired CharacterRepo rootRepo,
                         @Autowired ClassRepo classRepo,
                         @Autowired UserRepo userRepo,
                         @Autowired RoleRepo roleRepo){
        rootRepo.deleteAll();
        classRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
    }
}
