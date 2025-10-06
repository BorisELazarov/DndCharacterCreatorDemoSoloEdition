package com.example.dnd_character_creator_solo_edition.bll.dtos.auth;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationResponse(@NotBlank String token, @NotNull UserDTO user) {
}
