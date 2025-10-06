package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.auth.AuthenticationRequest;
import com.example.dnd_character_creator_solo_edition.bll.dtos.auth.AuthenticationResponse;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.RegisterDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/auth")
public class AuthenticationController {
    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterDTO registerDTO
    ){
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
