package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.SpellMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Spell;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpellMapperImpl implements SpellMapper {

    @Override
    public Spell fromDto(SpellDTO spellDTO) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell();
        spellDTO.id().ifPresent(spell::setId);
        spell.setName(spellDTO.name());
        spell.setDescription(spellDTO.description());
        spell.setLevel(spellDTO.level());
        spell.setCastingRange(spellDTO.castingRange());
        spell.setTarget(spellDTO.target());
        spell.setComponents(spellDTO.components());
        spell.setDuration(spellDTO.duration());
        spell.setCastingTime(spellDTO.castingTime());
        return spell;
    }
    @Override
    public SpellDTO toDto(Spell spell) {
        if(spell==null)
            return null;
        return new SpellDTO(
                spell.getId().describeConstable(),spell.getIsDeleted(),
                spell.getName(), spell.getLevel(),
                spell.getCastingTime(), spell.getCastingRange(),
                spell.getTarget(), spell.getComponents(),
                spell.getDuration(), spell.getDescription()
        );
    }

    @Override
    public List<Spell> fromDTOs(List<SpellDTO> spellDTOS) {

        return spellDTOS.stream().map(this::fromDto).toList();
    }

    @Override
    public List<SpellDTO> toDTOs(List<Spell> spells) {
        return spells.stream().map(this::toDto).toList();
    }
}
