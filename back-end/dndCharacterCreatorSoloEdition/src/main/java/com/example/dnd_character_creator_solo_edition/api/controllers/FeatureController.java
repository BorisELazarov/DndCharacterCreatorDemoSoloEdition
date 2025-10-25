package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.features.FeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.features.SearchFeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.FeatureService;
import com.example.dnd_character_creator_solo_edition.common.Constants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = Constants.crossOrigin)
@RequestMapping(path = "api/features")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<List<FeatureDTO>> getAll(@RequestBody SearchFeatureDTO search) {
        return new ResponseEntity<>(this.featureService.getAll(search, false), HttpStatus.OK);
    }

    @PostMapping(path = "/getAll/deleted")
    public ResponseEntity<List<FeatureDTO>> getAllDeleted(@RequestBody SearchFeatureDTO search) {
        return new ResponseEntity<>(this.featureService.getAll(search, true), HttpStatus.OK);
    }

    @GetMapping(path="{featureId}")
    public ResponseEntity<FeatureDTO> getFeature(@PathVariable("featureId") Long id){
        return new ResponseEntity<>(
                featureService.getFeature(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<FeatureDTO> addFeature(@RequestBody @Valid FeatureDTO featureDTO){
        return new ResponseEntity<>(
                featureService.addFeature(featureDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<FeatureDTO> updateFeature(@RequestBody @Valid FeatureDTO featureDTO){
        return new ResponseEntity<>(
                featureService.updateFeature(featureDTO),
                HttpStatus.OK
        );
    }

    @PutMapping(path="/restore/{featureId}")
    public ResponseEntity<Void> restoreFeature(
            @PathVariable("featureId") Long id){
        featureService.restoreFeature(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDeleteFeature(@RequestParam Long id) {
        featureService.softDeleteFeature(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/confirmedDelete")
    public ResponseEntity<Void> hardDeleteFeature(@RequestParam Long id) {
        featureService.hardDeleteFeature(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
