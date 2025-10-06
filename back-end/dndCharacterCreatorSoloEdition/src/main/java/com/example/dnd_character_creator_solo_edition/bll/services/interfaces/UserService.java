package com.example.dnd_character_creator_solo_edition.bll.services.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.SearchUserDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers(SearchUserDTO searchUserDTO);

    void changeUsername(Long id, String username);

    void changePassword(Long id, String oldPassword, String newPassword);

    void changeEmail(Long id, String email);

    void changeRole(Long id, String role);

    void softDeleteUser(Long id);

    void hardDeleteUser(Long id);

    void restoreUser(Long id);

    UserDTO getUser(Long id);
}
