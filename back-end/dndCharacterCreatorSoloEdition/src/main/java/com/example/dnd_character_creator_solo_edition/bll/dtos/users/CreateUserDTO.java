package com.example.dnd_character_creator_solo_edition.bll.dtos.users;

public record CreateUserDTO(Boolean isDeleted,
                            String username, String password,
                            String role) {
}
