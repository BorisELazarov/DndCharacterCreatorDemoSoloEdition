package com.example.dnd_character_creator_solo_edition.unitTests.mapperTests;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.implementations.UserMapperImpl;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.UserMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTests {
    private final UserMapper mapper=new UserMapperImpl();

    @Test
    void fromDtoAreEquals() {
        UserDTO dto=new UserDTO(Optional.of(1L),false,
                "username", "password","email@abv.bg",
                "user");
        Role role=new Role();
        role.setTitle("user");
        User entity= mapper.fromDto(dto, Optional.of(role));
        dto.id().ifPresent(id->assertEquals(id,entity.getId()));
        assertEquals(dto.isDeleted(),entity.getIsDeleted());
        assertEquals(dto.username(),entity.getUsername());
        assertEquals(dto.password(),entity.getPassword());
        assertEquals(dto.role(),role.getTitle());
    }

    @Test
    void toDtoAreEquals() {
        User entity=new User();
        entity.setId(4L);
        entity.setIsDeleted(true);
        entity.setUsername("Gosho");
        entity.setPassword("Ot edno do osem");
        entity.setEmail("email@abv.bg");
        Role role=new Role();
        role.setTitle("admin");
        entity.setRole(role);
        UserDTO dto=mapper.toDto(entity);
        dto.id().ifPresent(id->assertEquals(entity.getId(),id));
        assertEquals(entity.getIsDeleted(),dto.isDeleted());
        assertEquals(entity.getUsername(),dto.username());
        assertEquals(entity.getPassword(),dto.password());
        assertEquals(role.getTitle(),dto.role());
    }
}