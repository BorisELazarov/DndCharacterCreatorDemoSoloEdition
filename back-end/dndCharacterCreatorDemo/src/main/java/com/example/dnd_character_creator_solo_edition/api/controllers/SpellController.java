package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SearchSpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.SpellService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/spells")
public class SpellController {
    private final SpellService spellService;

    @Autowired
    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping
    public ResponseEntity<List<SpellDTO>> getSpells(){
        return new ResponseEntity<>(
                spellService.getSpellsUnfiltered(),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<List<SpellDTO>> getSpells(@RequestBody SearchSpellDTO searchSpellDTO){
        return new ResponseEntity<>(
                spellService.getSpells(false, searchSpellDTO),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/getAll/deleted")
    public ResponseEntity<List<SpellDTO>> getDeletedSpells(@RequestBody SearchSpellDTO searchSpellDTO){
        return new ResponseEntity<>(
                spellService.getSpells(true, searchSpellDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(path="/{spellId}")
    public ResponseEntity<SpellDTO> getSpell(@PathVariable("spellId") Long id) {
        return new ResponseEntity<>(
                spellService.getSpell(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<SpellDTO> postSpell(@RequestBody @Valid SpellDTO spell){
        return new ResponseEntity<>(
                spellService.addSpell(spell),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<SpellDTO> putSpell(@RequestBody @Valid SpellDTO spell){
        return new ResponseEntity<>(
                spellService.editSpell(spell),
                HttpStatus.OK
        );
    }

    @PutMapping(path = "/restore/{spellId}")
    public ResponseEntity<Void> restoreSpell(@PathVariable("spellId") Long id){
        spellService.restoreSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteSpell(@RequestParam Long id) {
        spellService.softDeleteSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteSpell(@RequestParam Long id){
        spellService.hardDeleteSpell(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
