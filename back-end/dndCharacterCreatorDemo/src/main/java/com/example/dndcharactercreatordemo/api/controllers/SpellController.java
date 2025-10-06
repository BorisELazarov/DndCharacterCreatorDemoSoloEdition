package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.spells.CreateSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.ReadSpellDTO;
import com.example.dndcharactercreatordemo.bll.dtos.spells.SaveSpellDTO;
import com.example.dndcharactercreatordemo.bll.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/spells")
public class SpellController {
    private final SpellService service;

    @Autowired
    public SpellController(SpellService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReadSpellDTO> getSpells(){
        return service.getSpells();
    }

    @GetMapping(path="/{spellId}")
    public ReadSpellDTO getSpell(@PathVariable("spellId") Long id) {
        return service.getSpell(id);
    }

    @PostMapping
    public void postSpell(@RequestBody CreateSpellDTO spell){
        service.addSpell(spell);
    }

    @PutMapping(path = "/{spellId}")
    public void putSpell(@RequestBody SaveSpellDTO spell,
                         @PathVariable("spellId") Long id){
        service.editSpell(spell, id);
    }

    @DeleteMapping(path="/{spellId}")
    public void deleteSpell(@PathVariable("spellId") Long id) {
        service.softDeleteSpell(id);
    }
}
