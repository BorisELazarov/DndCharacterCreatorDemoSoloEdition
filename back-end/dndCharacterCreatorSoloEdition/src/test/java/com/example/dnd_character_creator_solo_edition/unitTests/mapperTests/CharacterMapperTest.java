package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.*;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.*;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CharacterMapperTest {
    private final ProficiencyMapper proficiencyMapper=new ProficiencyMapperImpl();
    private final ClassMapper classMapper=new ClassMapperImpl(proficiencyMapper);
    private final UserMapper userMapper=new UserMapperImpl();
    private final SpellMapper spellMapper=new SpellMapperImpl();
    private final CharacterSpellMapper characterSpellMapper
            =new CharacterSpellMapperImpl(spellMapper);
    private final ProficiencyCharacterMapper proficiencyCharacterMapper
            =new ProficiencyCharacterMapperImpl(proficiencyMapper);
    private final CharacterMapper characterMapper=new CharacterMapperImpl(
            userMapper,
            classMapper,
            proficiencyCharacterMapper,
            characterSpellMapper
    );

    @Test
    void fromDto() {
        UserDTO userDTO=new UserDTO(
                Optional.of(2L),false,
                "Ganyo","ot 8 do 8",
                "email@abv.bg", "user"
        );
        ClassDTO classDTO=new ClassDTO(Optional.of(7L),
                false, "fighter",
                "Fights a lot", HitDiceEnum.D10,
                List.of()
        );
        CharacterDTO dto=new CharacterDTO(
                Optional.of(6L), true,
                "Konrad", userDTO,
                classDTO,(byte)3,
                (byte)12, (byte)16,
                (byte)10,(byte)14,
                (byte)8,(byte)14,
                Set.of(),Set.of()
        );
        Role role=new Role();
        role.setTitle(userDTO.role());
        Character character=characterMapper.fromDto(dto,Optional.of(role));
        dto.id().ifPresent(id->assertEquals(id,character.getId()));
        assertEquals(dto.name(),character.getName());
        assertEquals(dto.isDeleted(),character.getIsDeleted());
        assertEquals(dto.baseStr(),character.getBaseStr());
        assertEquals(dto.baseCha(),character.getBaseCha());
        assertEquals(dto.baseCon(),character.getBaseCon());
        assertEquals(dto.baseDex(),character.getBaseDex());
        assertEquals(dto.baseInt(),character.getBaseInt());
        assertEquals(dto.baseWis(),character.getBaseWis());
        assertEquals(dto.level(),character.getLevel());
        assertEquals(dto.dndClass(),classMapper.toDto(character.getDNDclass()));
        assertEquals(dto.user(),userMapper.toDto(character.getUser()));
    }

    @Test
    void toDto() {
        Role role=new Role();
        role.setTitle("role");
        User user=new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@abv.bg");
        user.setRole(role);
        DNDclass dclass=new DNDclass();
        dclass.setId(5L);
        dclass.setName("Wizard");
        dclass.setDescription("Master of SQL");
        dclass.setHitDice(HitDiceEnum.D10);
        dclass.setProficiencies(Set.of());
        Character character=new Character();
        character.setId(4L);
        character.setName("Vankata");
        character.setUser(user);
        character.setDNDclass(dclass);
        character.setLevel((byte)2);
        character.setBaseStr((byte)12);
        character.setBaseDex((byte)16);
        character.setBaseCon((byte) 10);
        character.setBaseInt((byte) 14);
        character.setBaseWis((byte) 8);
        character.setBaseCha((byte) 14);
        character.setCharacterSpells(List.of());
        character.setProficiencyCharacters(List.of());
        CharacterDTO dto=characterMapper.toDto(character);
        dto.id().ifPresent(id->assertEquals(character.getId(),id));
        assertEquals(character.getName(),dto.name());
        assertEquals(character.getIsDeleted(),dto.isDeleted());
        assertEquals(character.getBaseStr(),dto.baseStr());
        assertEquals(character.getBaseCha(),dto.baseCha());
        assertEquals(character.getBaseCon(),dto.baseCon());
        assertEquals(character.getBaseDex(),dto.baseDex());
        assertEquals(character.getBaseInt(),dto.baseInt());
        assertEquals(character.getBaseWis(),dto.baseWis());
        assertEquals(character.getLevel(),dto.level());
        assertEquals(character.getDNDclass(),classMapper.fromDto(dto.dndClass()));
        assertEquals(character.getUser(),userMapper.fromDto(dto.user()));
    }
}