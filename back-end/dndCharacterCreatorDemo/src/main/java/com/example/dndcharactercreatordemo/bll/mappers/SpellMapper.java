package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.CreateSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.ReadSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SaveSpellDTO;
import com.example.dndcharactercreatordemo.dal.entities.Spell;

import java.util.List;

public class SpellMapper implements IMapper<CreateSpellDTO, SaveSpellDTO, ReadSpellDTO, Spell>{
    @Override
    public Spell fromCreateDto(CreateSpellDTO spellDTO) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell();
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
    public Spell fromSaveDto(SaveSpellDTO spellDTO) {
        if (spellDTO==null)
            return null;
        Spell spell=new Spell();
        if (spellDTO.id().isPresent())
            spell.setId(spellDTO.id().get());
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
    public ReadSpellDTO toDto(Spell spell) {
        if(spell==null)
            return null;
        return new ReadSpellDTO(
                spell.getId(),spell.getIsDeleted(),
                spell.getName(), spell.getLevel(),
                spell.getCastingTime(), spell.getCastingRange(),
                spell.getTarget(), spell.getComponents(),
                spell.getDuration(), spell.getDescription()
        );
    }

    @Override
    public List<Spell> fromCreateDTOs(List<CreateSpellDTO> spellDTOS) {

        return spellDTOS.stream().map(this::fromCreateDto).toList();
    }

    @Override
    public List<Spell> fromSaveDTOs(List<SaveSpellDTO> spellDTOS) {

        return spellDTOS.stream().map(this::fromSaveDto).toList();
    }

    @Override
    public List<ReadSpellDTO> toDTOs(List<Spell> spells) {
        return spells.stream().map(this::toDto).toList();
    }
}
