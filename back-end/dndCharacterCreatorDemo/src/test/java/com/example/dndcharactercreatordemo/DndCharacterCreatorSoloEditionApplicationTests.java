package com.example.dndcharactercreatordemo;

import com.example.dndcharactercreatordemo.dal.entities.Proficiency;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DndCharacterCreatorSoloEditionApplicationTests {
	@PersistenceContext
	EntityManager manager;
	final ProficiencyRepo proficiencyRepo;

	public DndCharacterCreatorSoloEditionApplicationTests(ProficiencyRepo proficiencyRepo) {
		this.proficiencyRepo = proficiencyRepo;
	}

	@Test
	void contextLoads() {

	}

}
