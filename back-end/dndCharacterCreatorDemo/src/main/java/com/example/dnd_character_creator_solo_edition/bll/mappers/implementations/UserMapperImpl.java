package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.RegisterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.UserMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDto(UserDTO dto, Optional<Role> role) {
        if(dto==null)
            return null;
        User entity=fromDto(dto);
        role.ifPresent(entity::setRole);
        return entity;
    }

    @Override
    public User fromDto(UserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        dto.id().ifPresent(entity::setId);
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        entity.setEmail(dto.email());
        return entity;
    }

    @Override
    public User fromDto(RegisterDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        entity.setIsDeleted(false);
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        entity.setEmail(dto.email());
        return entity;
    }

    @Override
    public UserDTO toDto(User entity) {
        if(entity==null)
            return null;
        return new UserDTO(
                entity.getId().describeConstable(),
                entity.getIsDeleted(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getRole().getTitle()
        );
    }

    @Override
    public List<UserDTO> toDTOs(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
