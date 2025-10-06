package com.example.dndcharactercreatordemo.bll.dtos.users;

public record ReadUserDTO(Long id, boolean isDeleted, String username, String password, String title) {
}
