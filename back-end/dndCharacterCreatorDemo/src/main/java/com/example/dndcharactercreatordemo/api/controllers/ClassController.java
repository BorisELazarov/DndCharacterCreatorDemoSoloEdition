package com.example.dndcharactercreatordemo.api.controllers;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.CreateClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ReadClassDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import com.example.dndcharactercreatordemo.bll.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public List<ReadClassDTO> getClasses()
    {
        return classService.getClasses();
    }

    @GetMapping(path="{classId}")
    public ReadClassDTO getClass(@PathVariable("classId") Long id){
        return classService.getClass(id);
    }

    @PostMapping
    public void addClass(@RequestBody CreateClassDTO dndClass){
        classService.addClass(dndClass);
    }

    @PutMapping(path="{classId}")
    public void updateClass(
            @PathVariable("classId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) HitDiceEnum hitDice){
        classService.updateClass(id,name,description,hitDice);
    }

    @DeleteMapping(path="{classId}")
    public void deleteClass(@PathVariable("classId") Long id) {
        classService.deleteClass(id);
    }
}
