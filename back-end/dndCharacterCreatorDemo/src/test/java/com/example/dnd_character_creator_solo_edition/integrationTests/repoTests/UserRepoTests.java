package com.example.dnd_character_creator_solo_edition.integrationTests.repoTests;

import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.UserRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTests {
    private final UserRepo userRepo;


    @Autowired
    public UserRepoTests(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @BeforeAll
    static void seedUsers(@Autowired UserRepo seedRepo, @Autowired RoleRepo roleRepo) {
        Set<User> users=Set.of(
                getUser("Boris","BPass", "email@abv.bg"),
                getUser("Ivan","IPass","email2@abv.bg"),
                getUser("Georgi","GPass","email3@abv.bg"),
                getUser("Peter","PPass","email4@abv.bg")
        );
        users.stream().findFirst().get().setIsDeleted(true);
        roleRepo.saveAll(users.stream().map(User::getRole).toList());
        seedRepo.saveAll(users);
    }

    static User getUser(String username, String password, String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Role role=new Role();
        role.setTitle("user");
        user.setRole(new Role());
        return user;
    }


    @Test
    void findByUsernameAreEquals() {
        Optional<User> user = userRepo.findAll().stream()
                .filter(x -> !x.getIsDeleted()).findFirst();
        assertTrue(user.isPresent());
        Optional<User> foundUser = userRepo.findByUsername(user.get().getUsername());
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser);

    }

    @Test
    void findAllUndeletedReturnUndeletedCount() {
        List<User> users = userRepo.findAllUndeleted();
        long countAll = users.size();
        assertNotEquals(0, countAll);
        long countUndeleted = users.stream().filter(x -> !x.getIsDeleted()).count();
        assertEquals(countAll, countUndeleted);
    }

    @Test
    void findAllSoftDeletedReturnUndeletedCount() {
        List<User> users = userRepo.findAllSoftDeleted();
        long countAll = users.size();
        assertNotEquals(0, countAll);
        long countUndeleted = users.stream().filter(User::getIsDeleted).count();
        assertEquals(countAll, countUndeleted);
    }

    @Test
    void findDeletedByUsernameAndPasswordAreEquals(){
        Optional<User> user=userRepo.findAll()
                .stream().filter(User::getIsDeleted)
                .findFirst();
        assertTrue(user.isPresent());
        user.ifPresent(x->{
            Optional<User> foundUser=userRepo
                    .findDeletedByUsernameAndPassword(x.getUsername(),x.getPassword());
            assertTrue(foundUser.isPresent());
            foundUser.ifPresent(y->{
                assertTrue(y.getIsDeleted());
                assertEquals(x,y);
                assertEquals(x.getUsername(),y.getUsername());
                assertEquals(x.getPassword(),y.getPassword());
            });
        });
    }
    @Test
    void findByEmailAreEquals(){
        Optional<User> user=userRepo
                .findAll().stream()
                .filter(x->x.getEmail().equals("email@abv.bg"))
                .findFirst();
        assertTrue(user.isPresent());
        user.ifPresent(x->{
            Optional<User> foundUser=userRepo
                    .findByEmail(x.getEmail());
            assertTrue(foundUser.isPresent());
            foundUser.ifPresent(y->{
                assertEquals(x,y);
                assertEquals(x.getEmail(),y.getEmail());
            });
        });
    }

    @AfterAll
    static void rootUsers(@Autowired UserRepo rootRepo, @Autowired RoleRepo roleRepo){
        rootRepo.deleteAll();
        roleRepo.deleteAll();
    }
}
