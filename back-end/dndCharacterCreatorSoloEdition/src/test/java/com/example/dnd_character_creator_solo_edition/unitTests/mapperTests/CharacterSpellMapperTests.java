package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.CharacterSpellMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.SpellMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.CharacterSpellMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.SpellMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.*;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CharacterSpellMapperTests {
    private final SpellMapper spellMapper=new SpellMapperImpl();
    private final CharacterSpellMapper characterSpellMapper=
            new CharacterSpellMapperImpl(spellMapper);
    @Test
    void fromDto() {
        DNDclass dclass=new DNDclass();
        dclass.setId(5L);
        dclass.setName("Wizard");
        dclass.setDescription("Proficient with SQL");
        dclass.setHitDice(HitDiceEnum.D10);
        Character character=new Character();
        character.setId(4L);
        character.setName("Vankata");
        character.setDNDclass(dclass);
        character.setLevel((byte)2);
        character.setBaseStr((byte)12);
        character.setBaseDex((byte)16);
        character.setBaseCon((byte) 10);
        character.setBaseInt((byte) 14);
        character.setBaseWis((byte) 8);
        character.setBaseCha((byte) 14);
        SpellDTO spellDTO=new SpellDTO(Optional.of(6L), false, "Unit testing",
                9, "20 days",
                200, "My application",
                "V, S, M", 30,
                "Testing my application with unit test. Most powerful spell ever."
        );
        CharacterSpell characterSpell= characterSpellMapper.fromDto(spellDTO,character);
        assertEquals(character,characterSpell.getId().getCharacter());
        Spell spell= spellMapper.fromDto(spellDTO);
        assertEquals(spell, characterSpell.getId().getSpell());
    }
}