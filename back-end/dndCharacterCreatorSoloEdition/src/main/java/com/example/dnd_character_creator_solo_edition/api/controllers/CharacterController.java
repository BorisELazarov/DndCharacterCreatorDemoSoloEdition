package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.SearchCharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.CharacterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/characters")
public class CharacterController {
    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @PostMapping(path="/getForUser/{userId}")
    public ResponseEntity<List<CharacterDTO>> getCharacters(
            @PathVariable Long userId,
            @RequestBody SearchCharacterDTO searchCharacterDTO){
        return new ResponseEntity<>(
                service.getCharacters(userId,false, searchCharacterDTO),
            HttpStatus.OK
        );
    }

    @PostMapping(path = "/getForUser/deleted/{userId}")
    public ResponseEntity<List<CharacterDTO>> getDeletedCharacters(
            @PathVariable Long userId,
            @RequestBody SearchCharacterDTO searchCharacterDTO){
        return new ResponseEntity<>(
                service.getCharacters(userId,true, searchCharacterDTO),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> addCharacter(@RequestBody @Valid CharacterDTO characterDTO){
        return new ResponseEntity<>(
                service.addCharacter(characterDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<CharacterDTO> editCharacter(@RequestBody @Valid CharacterDTO characterDTO){
        return new ResponseEntity<>(
                service.editCharacter(characterDTO),
                HttpStatus.OK
        );
    }

    @PutMapping(path = "/restore/{characterId}")
    public ResponseEntity<Void> restoreCharacter(@PathVariable("characterId") Long id){
        service.restoreCharacter(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteCharacter(@RequestParam Long id){
        service.softDeleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteCharacter(@RequestParam Long id){
        service.hardDeleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{characterId}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable("characterId") Long id){
        return new ResponseEntity<>(
                service.getCharacterById(id),
                HttpStatus.OK
        );
    }
}
