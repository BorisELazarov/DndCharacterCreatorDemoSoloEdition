package com.example.dnd_character_creator_solo_edition.bll.dtos.users;

public record ReadUserDTO(Long id, boolean isDeleted, String username, String password, String title) {
}
