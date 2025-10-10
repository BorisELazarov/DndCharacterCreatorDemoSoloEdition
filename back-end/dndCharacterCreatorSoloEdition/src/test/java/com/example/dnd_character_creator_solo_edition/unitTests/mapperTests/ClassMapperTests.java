package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.ClassMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.ProficiencyMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ClassMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import com.example.dnd_character_creator_solo_edition.enums.ProfSubType;
import com.example.dnd_character_creator_solo_edition.enums.ProfType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassMapperTests {
    private final ProficiencyMapper proficiencyMapper=new ProficiencyMapperImpl();
    private final ClassMapper classMapper=new ClassMapperImpl(proficiencyMapper);
    private ProficiencyDTO getProficiencyDTO(Long id, boolean isDeleted, String name, ProfType type, ProfSubType subType) {
        return new ProficiencyDTO(Optional.of(id), isDeleted, name, type, subType);
    }

    private Proficiency getProficiency(Long id, boolean isDeleted,
                                       String name, ProfType type){
        Proficiency proficiency=new Proficiency();
        if (id!=null)
            proficiency.setId(id);
        proficiency.setIsDeleted(isDeleted);
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }

    @Test
    void fromDtoAreEqual(){
        List<ProficiencyDTO> proficiencyDTOS=List.of(
                getProficiencyDTO(1L,false,"athletics", ProfType.SKILL, null),
                getProficiencyDTO(6L,false,"martial", ProfType.WEAPON, null),
                getProficiencyDTO(8L,true,"heavy", ProfType.ARMOR, null)
        );
        ClassDTO dto=new ClassDTO(Optional.of(57L),
                false, "fighter",
                "Fights a lot", HitDiceEnum.D10,
                proficiencyDTOS
                );
        DNDclass entity=classMapper.fromDto(dto);
        dto.id().ifPresent(id->assertEquals(id,entity.getId()));
        assertEquals(dto.isDeleted(),entity.getIsDeleted());
        assertEquals(dto.name(),entity.getName());
        assertEquals(dto.description(),entity.getDescription());
        assertEquals(dto.hitDice(),entity.getHitDice());
        List<Proficiency> proficiencies=proficiencyMapper.fromDTOs(proficiencyDTOS);
        for (int i = 0; i <  dto.proficiencies().size(); i++) {
            ProficiencyDTO proficiencyDTO=dto.proficiencies().get(i);
            Proficiency proficiency= proficiencies.get(i);
            proficiencyDTO.id().ifPresent(id-> assertEquals(id,proficiency.getId()));
            assertEquals(proficiencyDTO.isDeleted(),proficiency.getIsDeleted());
            assertEquals(proficiencyDTO.name(),proficiency.getName());
            assertEquals(proficiencyDTO.type(),proficiency.getType());
        }
    }
    @Test
    void toDtoAreEqual(){
        Set<Proficiency> proficiencies= Set.of(
                getProficiency(1L,false,"athletics",ProfType.SKILL),
                getProficiency(6L,false,"martial", ProfType.WEAPON),
                getProficiency(8L,true,"heavy",ProfType.ARMOR)
        );
        DNDclass entity=new DNDclass();
        entity.setId(768L);
        entity.setIsDeleted(true);
        entity.setName("Barbarian");
        entity.setDescription("Bar bar bar");
        entity.setHitDice(HitDiceEnum.D12);
        entity.setProficiencies(proficiencies);
        ClassDTO dto=classMapper.toDto(entity);
        dto.id().ifPresent(id->assertEquals(entity.getId(),id));
        assertEquals(entity.getIsDeleted(),dto.isDeleted());
        assertEquals(entity.getName(),dto.name());
        assertEquals(entity.getDescription(),dto.description());
        assertEquals(entity.getHitDice(),dto.hitDice());
    }
}