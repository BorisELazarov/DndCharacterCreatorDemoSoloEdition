package com.example.dndcharactercreatordemo.bll.dtos.users;

public record CreateUserDTO(Boolean isDeleted,
                            String username, String password,
                            String role) {
}
