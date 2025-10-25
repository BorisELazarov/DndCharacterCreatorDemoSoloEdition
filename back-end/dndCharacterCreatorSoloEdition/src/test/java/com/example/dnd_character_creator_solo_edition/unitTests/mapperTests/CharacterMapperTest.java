package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.*;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.*;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CharacterMapperTest {
    private final ClassFeatureMapper featureMapper = new ClassFeatureMapperImpl(new FeatureMapperImpl());
    private final ProficiencyMapper proficiencyMapper = new ProficiencyMapperImpl();
    private final ClassMapper classMapper = new ClassMapperImpl(proficiencyMapper, featureMapper);
    private final SpellMapper spellMapper = new SpellMapperImpl();
    private final CharacterSpellMapper characterSpellMapper
            = new CharacterSpellMapperImpl(spellMapper);
    private final ProficiencyCharacterMapper proficiencyCharacterMapper
            = new ProficiencyCharacterMapperImpl(proficiencyMapper);
    private final CharacterMapper characterMapper = new CharacterMapperImpl(
            classMapper,
            proficiencyCharacterMapper,
            characterSpellMapper
    );

    @Test
    void fromDto() {
        ClassDTO classDTO = new ClassDTO(Optional.of(7L),
                false, "fighter",
                "Fights a lot", HitDiceEnum.D10,
                List.of(), List.of()
        );
        CharacterDTO dto = new CharacterDTO(
                Optional.of(6L), true,
                "Konrad",
                classDTO, (byte) 3,
                (byte) 12, (byte) 16,
                (byte) 10, (byte) 14,
                (byte) 8, (byte) 14,
                Set.of(), Set.of()
        );
        Character character = characterMapper.fromDto(dto);
        dto.id().ifPresent(id -> assertEquals(id, character.getId()));
        assertEquals(dto.name(), character.getName());
        assertEquals(dto.isDeleted(), character.getIsDeleted());
        assertEquals(dto.baseStr(), character.getBaseStr());
        assertEquals(dto.baseCha(), character.getBaseCha());
        assertEquals(dto.baseCon(), character.getBaseCon());
        assertEquals(dto.baseDex(), character.getBaseDex());
        assertEquals(dto.baseInt(), character.getBaseInt());
        assertEquals(dto.baseWis(), character.getBaseWis());
        assertEquals(dto.level(), character.getLevel());
        assertEquals(dto.dndClass(), classMapper.toDto(character.getDNDclass()));
    }

    @Test
    void toDto() {
        DNDclass dndClass = new DNDclass();
        dndClass.setId(5L);
        dndClass.setName("Wizard");
        dndClass.setDescription("Master of SQL");
        dndClass.setHitDice(HitDiceEnum.D10);
        dndClass.setProficiencies(Set.of());
        Character character = new Character();
        character.setId(4L);
        character.setName("Vankata");
        character.setDNDclass(dndClass);
        character.setLevel((byte) 2);
        character.setBaseStr((byte) 12);
        character.setBaseDex((byte) 16);
        character.setBaseCon((byte) 10);
        character.setBaseInt((byte) 14);
        character.setBaseWis((byte) 8);
        character.setBaseCha((byte) 14);
        character.setCharacterSpells(List.of());
        character.setProficiencyCharacters(List.of());
        CharacterDTO dto = characterMapper.toDto(character);
        dto.id().ifPresent(id -> assertEquals(character.getId(), id));
        assertEquals(character.getName(), dto.name());
        assertEquals(character.getIsDeleted(), dto.isDeleted());
        assertEquals(character.getBaseStr(), dto.baseStr());
        assertEquals(character.getBaseCha(), dto.baseCha());
        assertEquals(character.getBaseCon(), dto.baseCon());
        assertEquals(character.getBaseDex(), dto.baseDex());
        assertEquals(character.getBaseInt(), dto.baseInt());
        assertEquals(character.getBaseWis(), dto.baseWis());
        assertEquals(character.getLevel(), dto.level());
        assertEquals(character.getDNDclass(), classMapper.fromDto(dto.dndClass()));
    }
}