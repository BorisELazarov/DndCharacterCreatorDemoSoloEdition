package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.users.CreateUserDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.ReadUserDTO;
import com.example.dndcharactercreatordemo.bll.dtos.users.SaveUserDTO;
import com.example.dndcharactercreatordemo.dal.entities.Role;
import com.example.dndcharactercreatordemo.dal.entities.User;
import com.example.dndcharactercreatordemo.dal.repos.RoleRepo;

import java.util.List;

public class UserMapper implements IMapper<CreateUserDTO, SaveUserDTO, ReadUserDTO, User>{
    private final RoleRepo roleRepo;

    public UserMapper(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public User fromCreateDto(CreateUserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        Role role=roleRepo.findByTitle(dto.role());
        role.setTitle(dto.role());
        entity.setRole(role);
        return entity;
    }

    @Override
    public User fromSaveDto(SaveUserDTO dto) {
        if(dto==null)
            return null;
        User entity=new User();
        if (dto.id().isPresent())
            entity.setId(dto.id().get());
        entity.setIsDeleted(dto.isDeleted());
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        Role role=roleRepo.findByTitle(dto.role());
        role.setTitle(dto.role());
        entity.setRole(role);
        return entity;
    }

    @Override
    public ReadUserDTO toDto(User entity) {
        if(entity==null)
            return null;
        return new ReadUserDTO(
                entity.getId(),
                entity.getIsDeleted(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole().getTitle()
        );
    }

    @Override
    public List<User> fromSaveDTOs(List<SaveUserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::fromSaveDto)
                .toList();
    }

    @Override
    public List<User> fromCreateDTOs(List<CreateUserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::fromCreateDto)
                .toList();
    }

    @Override
    public List<ReadUserDTO> toDTOs(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
