package com.example.dnd_character_creator_solo_edition.bll.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String email, @NotBlank String password){
}
