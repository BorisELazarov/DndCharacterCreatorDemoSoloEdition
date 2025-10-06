package com.example.dndcharactercreatordemo;

import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DndCharacterCreatorDemoApplicationTests {
	@PersistenceContext
	EntityManager manager;
	final ProficiencyRepo proficiencyRepo;

	public DndCharacterCreatorDemoApplicationTests(ProficiencyRepo proficiencyRepo) {
		this.proficiencyRepo = proficiencyRepo;
	}

	@Test
	void contextLoads() {
//		Proficiency proficiency=new Proficiency();
//		proficiency.setName("Something");
//		proficiency.setType("Anything");
//		manager.merge(proficiency);

	}

}
