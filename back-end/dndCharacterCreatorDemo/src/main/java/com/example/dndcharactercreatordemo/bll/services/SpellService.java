package com.example.dndcharactercreatordemo.bll.services;

import com.example.dndcharactercreatordemo.bll.dtos.spells.CreateSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.ReadSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SaveSpellDTO;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.bll.mappers.SpellMapper;
import com.example.dndcharactercreatordemo.dal.entities.Spell;
import com.example.dndcharactercreatordemo.dal.repos.SpellRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpellService {
    private final SpellRepo repo;
    private final IMapper<CreateSpellDTO, SaveSpellDTO, ReadSpellDTO, Spell> mapper;

    @Autowired
    public SpellService(SpellRepo repo) {
        this.repo = repo;
        this.mapper = new SpellMapper();
    }

    private void spellNotFound(){
        throw new IllegalArgumentException("Spell not found!");
    }

    public List<ReadSpellDTO> getSpells() {
        return mapper.toDTOs(repo.findAll());
    }

    public ReadSpellDTO getSpell(Long id){
        Optional<Spell> spell=repo.findById(id);
        if (spell.isEmpty())
            spellNotFound();
        return mapper.toDto(spell.get());
    }

    public void addSpell(CreateSpellDTO spellDTO){
        Spell spell=repo.findByName(spellDTO.name());
        if (spell!=null && !spell.getIsDeleted()){
            throw new IllegalArgumentException("Error: there is already spell with such name!");
        }
        repo.save(mapper.fromCreateDto(spellDTO));
    }

    public void editSpell(SaveSpellDTO spellDTO, Long id) {
        if (repo.existsById(id))
            repo.save(mapper.fromSaveDto(spellDTO));
        else
            spellNotFound();
    }

    public void softDeleteSpell(Long id){
        Optional<Spell> optionalSpell=repo.findById(id);
        if (optionalSpell.isPresent()){
            Spell spell=optionalSpell.get();
            spell.setIsDeleted(true);
            repo.save(spell);

        }else {
            spellNotFound();
        }
    }
}
