package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.CharacterSpellMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.SpellMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.CharacterSpell;
import com.example.dnd_character_creator_solo_edition.dal.entities.composite_ids.CharacterSpellPairId;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterSpellMapperImpl implements CharacterSpellMapper {
    private final SpellMapper spellMapper;

    public CharacterSpellMapperImpl(@NotNull SpellMapper spellMapper) {
        this.spellMapper = spellMapper;
    }

    @Override
    public CharacterSpell fromDto(SpellDTO spellDTO, Character character) {
        CharacterSpell characterSpell=new CharacterSpell();
        CharacterSpellPairId id=new CharacterSpellPairId();
        id.setSpell(spellMapper.fromDto(spellDTO));
        id.setCharacter(character);
        characterSpell.setId(id);
        return characterSpell;
    }

    @Override
    public List<CharacterSpell> fromDTOs(Set<SpellDTO> spellDTOs, Character character) {
        return spellDTOs.stream().map(x-> fromDto(x,character)).toList();
    }

    @Override
    public SpellDTO toDto(CharacterSpell characterSpell) {
        return spellMapper.toDto(characterSpell.getId().getSpell());
    }

    @Override
    public Set<SpellDTO> toDTOs(List<CharacterSpell> characterSpells) {
        return characterSpells.stream().map(this::toDto).collect(Collectors.toSet());
    }
}
