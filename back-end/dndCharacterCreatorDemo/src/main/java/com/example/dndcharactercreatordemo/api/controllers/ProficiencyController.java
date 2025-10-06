package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.CreateProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.services.ProficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/proficiencies")
public class ProficiencyController {
    private final ProficiencyService proficiencyService;

    @Autowired
    public ProficiencyController(ProficiencyService proficiencyService) {
        this.proficiencyService = proficiencyService;
    }

    @GetMapping
    public List<ReadProficiencyDTO> getProficiencies()
    {
        return proficiencyService.getProficiencies();
    }

    @GetMapping(path="{proficiencyId}")
    public ReadProficiencyDTO getProficiency(@PathVariable("proficiencyId") Long id){
        return proficiencyService.getProficiency(id);
    }

    @PostMapping
    public void addProficiency(@RequestBody CreateProficiencyDTO proficiency){
        proficiencyService.addProficiency(proficiency);
    }

    @PutMapping(path="{proficiencyId}")
    public void updateProficiency(
            @PathVariable("proficiencyId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type){
        proficiencyService.updateProficiency(id,name,type);
    }

    @DeleteMapping(path="{proficiencyId}")
    public void deleteProficiency(@PathVariable("proficiencyId") Long id) {
        proficiencyService.deleteProficiency(id);
    }
}
