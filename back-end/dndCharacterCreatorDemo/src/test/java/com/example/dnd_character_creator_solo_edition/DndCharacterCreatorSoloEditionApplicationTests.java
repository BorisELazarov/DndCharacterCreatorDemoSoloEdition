package com.example.dnd_character_creator_solo_edition;

import com.example.dnd_character_creator_solo_edition.dal.repos.ProficiencyRepo;
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
