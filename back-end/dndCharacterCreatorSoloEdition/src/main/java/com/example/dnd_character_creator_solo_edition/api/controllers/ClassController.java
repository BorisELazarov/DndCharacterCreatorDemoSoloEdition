package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.SearchClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.ClassService;
import com.example.dnd_character_creator_solo_edition.common.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = Constants.crossOrigin)
@RequestMapping(path="api/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getClassesUnfiltered()
    {
        return new ResponseEntity<>(
                classService.getClassesUnfiltered(),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<List<ClassDTO>> getClasses(
            @RequestBody SearchClassDTO searchClassDTO
            )
    {
        return new ResponseEntity<>(
            classService.getClasses(false, searchClassDTO),
            HttpStatus.OK
        );
    }

    @PostMapping(path = "/getAll/deleted")
    public ResponseEntity<List<ClassDTO>> getDeletedClasses(
            @RequestBody SearchClassDTO searchClassDTO
    )
    {
        return new ResponseEntity<>(
                classService.getClasses(true, searchClassDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(path="{classId}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable("classId") Long id){

        return new ResponseEntity<>(
                classService.getClassById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ClassDTO> addClass(@Valid @RequestBody ClassDTO classDTO){
        return new ResponseEntity<>(
                classService.addClass(classDTO),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClassDTO> updateClass(
            @RequestBody ClassDTO classDTO){
        return new ResponseEntity<>(
                classService.updateClass(classDTO),
                HttpStatus.OK);
    }

    @PutMapping(path="restore/{classId}")
    public ResponseEntity<Void> restoreClass(
            @PathVariable("classId") Long id){
        classService.restoreClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteClass(@RequestParam Long id) {
        classService.softDeleteClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path="/confirmedDelete")
    public ResponseEntity<Void> hardDeleteClass(@RequestParam Long id) {
        classService.hardDeleteClass(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
