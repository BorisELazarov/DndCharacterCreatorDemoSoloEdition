package com.example.dnd_character_creator_solo_edition.bll.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record UserDTO(Optional<Long> id, Boolean isDeleted,
                      @NotNull(message = "Username must not be null")
                      @Size(min=3, max = 50)
                      String username,
                      @NotNull(message = "Password must not be null")
                      @Size(min=8, max = 50)
                      String password,
                      @NotNull(message = "Email must not be null")
                      @Size(min = 6, max=320)
                      @Email
                      String email,
                      @NotNull(message = "Role title must not be null")
                      @Size(min=4, max = 20)
                      String role){}
