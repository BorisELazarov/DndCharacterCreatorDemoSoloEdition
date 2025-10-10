package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.ProficiencyCharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.ProficiencyCharacterMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.ProficiencyMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyCharacterMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProficiencyCharacter;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProficiencyCharacterPairId;
import com.example.dnd_character_creator_solo_edition.enums.ProfType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProficiencyCharacterMapperTests {
    private final ProficiencyMapper proficiencyMapper=new ProficiencyMapperImpl();
    private final ProficiencyCharacterMapper proficiencyCharacterMapper
            =new ProficiencyCharacterMapperImpl(proficiencyMapper);

//    @Test
//    void fromDto() {
//        ProficiencyDTO heavyArmor=new ProficiencyDTO(
//                Optional.of(1L),
//                true,
//                "heavy",
//                "armor"
//        );
//        ProficiencyCharacterDTO dto=new ProficiencyCharacterDTO(
//                heavyArmor,
//                true
//        );
//        ProficiencyCharacter entity=proficiencyCharacterMapper.fromDto(dto);
//        dto.proficiency().id().ifPresent(id->assertEquals(id,entity.getId().getProficiency().));
//        assertEquals(dto.isDeleted(),entity.getIsDeleted());
//        assertEquals(dto.name(),entity.getName());
//        assertEquals(dto.level(),entity.getLevel());
//        assertEquals(dto.castingTime(),entity.getCastingTime());
//        assertEquals(dto.castingRange(),entity.getCastingRange());
//        assertEquals(dto.target(),entity.getTarget());
//        assertEquals(dto.components(),entity.getComponents());
//        assertEquals(dto.duration(),entity.getDuration());
//        assertEquals(dto.description(),entity.getDescription());
//    }

    @Test
    void toDto() {
        ProficiencyCharacterPairId pairId=new ProficiencyCharacterPairId();
        Proficiency proficiency= new Proficiency();
        proficiency.setId(1L);
        proficiency.setIsDeleted(false);
        proficiency.setName("heavy");
        proficiency.setType(ProfType.ARMOR);
        pairId.setProficiency(proficiency);
        ProficiencyCharacter entity=new ProficiencyCharacter();
        entity.setExpertise(false);
        entity.setId(pairId);
        ProficiencyCharacterDTO dto=proficiencyCharacterMapper.toDto(entity);
        dto.proficiency().id().ifPresent(id->assertEquals(proficiency.getId(),id));
        assertEquals(proficiency.getIsDeleted(),dto.proficiency().isDeleted());
        assertEquals(proficiency.getName(),dto.proficiency().name());
        assertEquals(proficiency.getType(),dto.proficiency().type());
        assertEquals(entity.getExpertise(),dto.expertise());
    }
}