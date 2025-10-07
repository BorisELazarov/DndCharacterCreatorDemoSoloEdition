package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.SpellMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.SpellMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Spell;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpellMapperTests {
    private final SpellMapper mapper=new SpellMapperImpl();
    static Spell getSpell(Long id,
            Boolean isDeleted,
                          String name, int level,
                          String castingTime, int castingRange,
                          String target, String components,
                          int duration, String description){
        Spell spell=new Spell();
        spell.setId(id);
        spell.setIsDeleted(isDeleted);
        spell.setName(name);
        spell.setDescription(description);
        spell.setLevel(level);
        spell.setCastingRange(castingRange);
        spell.setTarget(target);
        spell.setComponents(components);
        spell.setDuration(duration);
        spell.setCastingTime(castingTime);
        return spell;
    }
    @Test
    void fromDtoAreEquals(){
        SpellDTO dto=new SpellDTO(Optional.of(1L), false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder"
        );
        Spell entity=mapper.fromDto(dto);
        dto.id().ifPresent(id->assertEquals(id,entity.getId()));
        assertEquals(dto.isDeleted(),entity.getIsDeleted());
        assertEquals(dto.name(),entity.getName());
        assertEquals(dto.level(),entity.getLevel());
        assertEquals(dto.castingTime(),entity.getCastingTime());
        assertEquals(dto.castingRange(),entity.getCastingRange());
        assertEquals(dto.target(),entity.getTarget());
        assertEquals(dto.components(),entity.getComponents());
        assertEquals(dto.duration(),entity.getDuration());
        assertEquals(dto.description(),entity.getDescription());
    }
    @Test
    void toDtoAreEquals(){
        Spell entity=getSpell(1L, false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder"
        );
        SpellDTO dto=mapper.toDto(entity);
        dto.id().ifPresent(id->assertEquals(entity.getId(), id));
        assertEquals(entity.getIsDeleted(),dto.isDeleted());
        assertEquals(entity.getName(),dto.name());
        assertEquals(entity.getLevel(),dto.level());
        assertEquals(entity.getCastingTime(),dto.castingTime());
        assertEquals(entity.getCastingRange(),dto.castingRange());
        assertEquals(entity.getTarget(),dto.target());
        assertEquals(entity.getComponents(),dto.components());
        assertEquals(entity.getDuration(),dto.duration());
        assertEquals(entity.getDescription(),dto.description());
    }
}
