package com.example.dnd_character_creator_solo_edition.dal.configs;

import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.dal.repos.ProficiencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    private final ProficiencyRepo proficiencyRepo;

    @Autowired
    public Config(ProficiencyRepo proficiencyRepo) {
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

    @Bean
    public CommandLineRunner seedData(){
        return args -> addProficiencies();
    }
}
