package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.SearchProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.ProficiencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="api/proficiencies")
public class ProficiencyController {
    private final ProficiencyService proficiencyService;

    @Autowired
    public ProficiencyController(ProficiencyService proficiencyService) {
        this.proficiencyService = proficiencyService;
    }

    @GetMapping
    public ResponseEntity<List<ProficiencyDTO>> getProficienciesUnfiltered()
    {
        return new ResponseEntity<>(
                proficiencyService.getProficienciesUnfiltered(),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<List<ProficiencyDTO>> getProficiencies(
            @RequestBody SearchProficiencyDTO searchProficiencyDTO
            )
    {
        return new ResponseEntity<>(
                proficiencyService.getProficiencies(false, searchProficiencyDTO),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/getAll/deleted")
    public ResponseEntity<List<ProficiencyDTO>> getDeletedProficiencies(
            @RequestBody SearchProficiencyDTO searchProficiencyDTO
    )
    {
        return new ResponseEntity<>(
                proficiencyService.getProficiencies(true, searchProficiencyDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(path="{proficiencyId}")
    public ResponseEntity<ProficiencyDTO> getProficiency(@PathVariable("proficiencyId") Long id){
        return new ResponseEntity<>(
                proficiencyService.getProficiency(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ProficiencyDTO> addProficiency(@RequestBody @Valid ProficiencyDTO proficiencyDTO){
        return new ResponseEntity<>(
                proficiencyService.addProficiency(proficiencyDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<ProficiencyDTO> updateProficiency(@Valid @RequestBody ProficiencyDTO proficiencyDTO){
        return new ResponseEntity<>(
                proficiencyService.updateProficiency(proficiencyDTO),
                HttpStatus.OK
        );
    }

    @PutMapping(path="/restore/{proficiencyId}")
    public ResponseEntity<Void> restoreProficiency(
            @PathVariable("proficiencyId") Long id){
        proficiencyService.restoreProficiency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteProficiency(@RequestParam Long id) {
        proficiencyService.softDeleteProficiency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteProficiency(@RequestParam Long id) {
        proficiencyService.hardDeleteProficiency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getTypes")
    public ResponseEntity<List<String>> getTypes() {
        return new ResponseEntity<>(this.proficiencyService.getTypes(), HttpStatus.OK);
    }
}
