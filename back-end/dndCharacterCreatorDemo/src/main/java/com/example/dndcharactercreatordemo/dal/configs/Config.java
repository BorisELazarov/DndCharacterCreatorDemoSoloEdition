package com.example.dndcharactercreatordemo.dal.configs;

import com.example.dndcharactercreatordemo.dal.entities.Privilege;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;
import com.example.dndcharactercreatordemo.dal.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Configuration
public class Config {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ProficiencyRepo proficiencyRepo;

    @Autowired
    public Config(UserRepo userRepo, RoleRepo roleRepo, ProficiencyRepo proficiencyRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.proficiencyRepo = proficiencyRepo;
    }

    private Proficiency addProficiency(String name, String type){
        Proficiency proficiency=new Proficiency();
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }

    private void addProficiencies(){
        if (proficiencyRepo.count()>0)
            return;
        String language="Language";
        List<Proficiency> proficiencies = new ArrayList<>();
        proficiencies.add(addProficiency("Common",language));
        proficiencies.add(addProficiency("Elven",language));
        proficiencies.add(addProficiency("Dwarvish",language));
        proficiencies.add(addProficiency("Orcish",language));
        proficiencies.add(addProficiency("Celestial",language));
        proficiencies.add(addProficiency("Infernal",language));
        proficiencyRepo.saveAll(proficiencies);
    }

    //To optimise
    private void addRoles() {
        if (roleRepo.count()<1) {
            Privilege manageProfile = new Privilege();
            manageProfile.setTitle("manage profile");
            Privilege manageUsers = new Privilege();
            manageUsers.setTitle("manage users");
            Privilege manageCharacters = new Privilege();
            manageCharacters.setTitle("manage characters");
            Privilege manageData = new Privilege();
            manageData.setTitle("manage data");
            Role admin = new Role();
            admin.setTitle("admin");
            admin.setPrivileges(new LinkedHashSet<>());
            admin.getPrivileges().add(manageProfile);
            admin.getPrivileges().add(manageUsers);
            Role dataManager = new Role();
            dataManager.setTitle("data manager");
            dataManager.setPrivileges(new LinkedHashSet<>());
            dataManager.getPrivileges().add(manageProfile);
            dataManager.getPrivileges().add(manageData);
            Role user = new Role();
            user.setTitle("user");
            user.setPrivileges(new LinkedHashSet<>());
            user.getPrivileges().add(manageProfile);
            user.getPrivileges().add(manageCharacters);
            List<Role> roles=new ArrayList<>();
            roles.add(user);
            roles.add(dataManager);
            roles.add(admin);
            roleRepo.saveAll(roles);
        }
    }

    private void addUser(){
        if (userRepo.findByUsername("Boris")==null) {
            User user = new User();
            user.setUsername("Boris");
            user.setPassword("BPass");
            Role role= roleRepo.findByTitle("admin");
            if (role!=null)
                user.setRole(role);
            userRepo.save(user);
        }
    }

    @Bean
    public CommandLineRunner seedData(){
        return args -> {
            addProficiencies();
            addRoles();
            addUser();
        };
    }
}
