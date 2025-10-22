package com.example.dnd_character_creator_solo_edition.dal.configs;

import com.example.dnd_character_creator_solo_edition.dal.entities.ProfType;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProfSubTypeRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProfTypeRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProficiencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    private final ProficiencyRepo proficiencyRepo;
    private final ProfTypeRepo profTypeRepo;
    private final ProfSubTypeRepo profSubTypeRepo;

    @Autowired
    public Config(ProficiencyRepo proficiencyRepo, ProfTypeRepo profTypeRepo, ProfSubTypeRepo profSubTypeRepo) {
        this.proficiencyRepo = proficiencyRepo;
        this.profTypeRepo = profTypeRepo;
        this.profSubTypeRepo = profSubTypeRepo;
    }


    private Proficiency addProficiency(String name, ProfType type){
        Proficiency proficiency=new Proficiency();
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }

    private ProfType addProfType(String language) {
        ProfType profType = new ProfType();
        profType.setName(language);
        return profTypeRepo.save(profType);
    }

    private void addProficiencies() {
        if (proficiencyRepo.count()>0)
            return;
        String language = "Language";
        ProfType profType = profTypeRepo.findByName(language).orElseGet(() -> addProfType(language));
        List<Proficiency> proficiencies = new ArrayList<>();
        proficiencies.add(addProficiency("Common", profType));
        proficiencies.add(addProficiency("Elven", profType));
        proficiencies.add(addProficiency("Dwarvish", profType));
        proficiencies.add(addProficiency("Orcish", profType));
        proficiencies.add(addProficiency("Celestial", profType));
        proficiencies.add(addProficiency("Infernal", profType));
        proficiencyRepo.saveAll(proficiencies);
    }

    @Bean
    public CommandLineRunner seedData(){
        return args -> addProficiencies();
    }
}
