package com.example.dnd_character_creator_solo_edition.bll.dtos.users;

import java.util.Optional;

public record SaveUserDTO(Optional<Long> id, Boolean isDeleted,
                          String username, String password,
                          String role){

}
