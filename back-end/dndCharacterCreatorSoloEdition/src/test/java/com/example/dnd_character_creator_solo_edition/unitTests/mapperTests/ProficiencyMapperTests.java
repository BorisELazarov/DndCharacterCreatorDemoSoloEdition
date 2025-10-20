package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.ProficiencyMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProficiencyMapperTests {
    private final ProficiencyMapper mapper=new ProficiencyMapperImpl();

    @Test
    void fromDtoAreEquals(){
        ProficiencyDTO heavyArmor=new ProficiencyDTO(
                Optional.of(1L),
                true,
                "heavy",
                "armor"
                );
        Proficiency proficiency= mapper.fromDto(heavyArmor);
        heavyArmor.id().ifPresent(id-> assertEquals(id,proficiency.getId()));
        assertEquals(heavyArmor.isDeleted(),proficiency.getIsDeleted());
        assertEquals(heavyArmor.name(),proficiency.getName());
        assertEquals(heavyArmor.type(),proficiency.getType());
    }

    @Test
    void toDtoAreEquals(){
        Proficiency proficiency= new Proficiency();
        proficiency.setId(1L);
        proficiency.setIsDeleted(false);
        proficiency.setName("heavy");
        proficiency.setType("armor");
        ProficiencyDTO dto=mapper.toDto(proficiency);
        assertTrue(dto.id().isPresent());
        assertEquals(proficiency.getId(),dto.id().get());
        assertEquals(proficiency.getIsDeleted(),dto.isDeleted());
        assertEquals(proficiency.getName(),dto.name());
        assertEquals(proficiency.getType(),dto.type());
    }

    @Test
    void fromDTOsAreEquals(){
        ProficiencyDTO heavyArmor=new ProficiencyDTO(
                Optional.of(1L),
                true,
                "heavy",
                "armor"
        );
        ProficiencyDTO lightArmor=new ProficiencyDTO(
                Optional.of(2L),
                false,
                "light",
                "armor"
        );
        List<ProficiencyDTO> proficiencyDTOS=List.of(heavyArmor, lightArmor);
        List<Proficiency> proficiencies=mapper.fromDTOs(proficiencyDTOS);
        for (int i = 0; i <  proficiencyDTOS.size(); i++) {
            ProficiencyDTO dto=proficiencyDTOS.get(i);
            Proficiency proficiency= proficiencies.get(i);
            dto.id().ifPresent(id-> assertEquals(id,proficiency.getId()));
            assertEquals(dto.isDeleted(),proficiency.getIsDeleted());
            assertEquals(dto.name(),proficiency.getName());
            assertEquals(dto.type(),proficiency.getType());
        }
    }

    @Test
    void toDTOsAreEquals(){
        Proficiency heavyArmor=new Proficiency();
        heavyArmor.setId(5L);
        heavyArmor.setIsDeleted(true);
        heavyArmor.setName("heavy");
        heavyArmor.setType("burden");
        Proficiency lightArmor=new Proficiency();
        lightArmor.setId(2L);
        lightArmor.setIsDeleted(false);
        lightArmor.setName("light");
        lightArmor.setType("annoyance");
        List<Proficiency> proficiencies=List.of(heavyArmor, lightArmor);
        List<ProficiencyDTO> proficiencyDTOS=mapper.toDTOs(proficiencies);
        for (int i = 0; i <  proficiencies.size(); i++) {
            ProficiencyDTO dto=proficiencyDTOS.get(i);
            Proficiency proficiency= proficiencies.get(i);
            dto.id().ifPresent(id-> assertEquals(proficiency.getId(), id));
            assertEquals(proficiency.getIsDeleted(),dto.isDeleted());
            assertEquals(proficiency.getName(),dto.name());
            assertEquals(proficiency.getType(),dto.type());
        }
    }
}
