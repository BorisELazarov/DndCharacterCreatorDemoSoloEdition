package com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.RegisterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    User fromDto(UserDTO userDTO, Optional<Role> role);
    User fromDto(UserDTO userDTO);
    User fromDto(RegisterDTO registerDTO);
    UserDTO toDto(User user);
    List<UserDTO> toDTOs(List<User> entities);
}
