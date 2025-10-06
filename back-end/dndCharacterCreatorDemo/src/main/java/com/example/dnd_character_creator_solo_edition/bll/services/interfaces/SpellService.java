package com.example.dnd_character_creator_solo_edition.bll.services.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SearchSpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;

import java.util.List;

public interface SpellService {
    List<SpellDTO> getSpells(boolean isDeleted,
                             SearchSpellDTO searchSpellDTO);

    SpellDTO getSpell(Long id);

    SpellDTO addSpell(SpellDTO spellDTO);

    SpellDTO editSpell(SpellDTO spellDTO);

    void softDeleteSpell(Long id);

    void hardDeleteSpell(Long id);

    void restoreSpell(Long id);

    List<SpellDTO> getSpellsUnfiltered();
}
