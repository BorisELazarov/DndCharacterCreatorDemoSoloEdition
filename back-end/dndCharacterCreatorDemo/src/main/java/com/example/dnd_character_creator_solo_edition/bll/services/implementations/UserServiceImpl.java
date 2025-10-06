package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.SearchUserDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.UserMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.UserService;
import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.UserRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND_MESSAGE="The user is not found!";
    private static final String USERNAME_TAKEN_MESSAGE="This username is already taken!";
    private final UserRepo userRepo;
    private final UserMapper mapper;
    private final RoleRepo roleRepo;
    @PersistenceContext
    private EntityManager em;
    public UserServiceImpl(@NotNull UserRepo userRepo, @NotNull RoleRepo roleRepo,
                           @NotNull UserMapper mapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @Override
    public List<UserDTO> getUsers(SearchUserDTO searchUserDTO) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery= cb.createQuery(User.class);
        Root<User> root= criteriaQuery.from(User.class);
        Join<User,Role> joinRoles= root.join("role",JoinType.INNER);
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.and(
                                cb.like(root.get("username"),cb.parameter(String.class,"usernameParam")),
                        cb.like(joinRoles.get("title"),cb.parameter(String.class,"roleParam"))
                        ),
                        cb.like(root.get("email"),cb.parameter(String.class,"emailParam"))
                ));
        String sortBy= searchUserDTO.sort().sortBy();
        if (sortBy.isEmpty()){
            sortBy="id";
        }
        if (searchUserDTO.sort().ascending()){
            criteriaQuery.orderBy(cb.asc(root.get(sortBy)));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortBy)));
        }
        TypedQuery<User> query = em.createQuery(criteriaQuery);
        query.setParameter("usernameParam","%"+searchUserDTO.filter().username()+"%");
        query.setParameter("emailParam","%"+searchUserDTO.filter().email()+"%");
        query.setParameter("roleParam","%"+searchUserDTO.filter().roleTitle()+"%");
        List<User> users=query.getResultList();
        return mapper.toDTOs(users);
    }

    @Override
    public void changeUsername(Long id, String username) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        if (username != null &&
                username.length() > 0 &&
                !username.equals(foundUser.getUsername())) {
            Optional<User> userByUsername = userRepo.findByUsername(username);
            if (userByUsername.isPresent()) {
                throw new NameAlreadyTakenException(USERNAME_TAKEN_MESSAGE);
            }
            foundUser.setUsername(username);
            userRepo.save(foundUser);
        }
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        oldPassword=String.valueOf(oldPassword.hashCode());

        if (!foundUser.getPassword().equals(oldPassword))
            throw new WrongPasswordException("Wrong password");
        if (newPassword != null &&
                newPassword.length() > 0) {
            foundUser.setPassword(String.valueOf(newPassword.hashCode()));
            userRepo.save(foundUser);
        }
    }

    @Override
    public void changeEmail(Long id, String email) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        if (userRepo.findByEmail(email).isPresent()){
            throw new EmailAlreadyTakenException("");
        }
        foundUser.setEmail(email);
        userRepo.save(foundUser);
    }

    @Override
    public void changeRole(Long id, String role) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        User foundUser = optionalUser.get();
        Optional<Role> foundRole=roleRepo.findByTitle(role);
        if (foundRole.isEmpty()){
            throw new NotFoundException("There is no such role!");
        }
        foundUser.setRole(foundRole.get());
        userRepo.save(foundUser);
    }

    @Override
    public void softDeleteUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            foundUser.setIsDeleted(true);
            userRepo.save(foundUser);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

    }

    @Override
    @Transactional
    public void hardDeleteUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            if (foundUser.getIsDeleted()) {
                userRepo.delete(foundUser);
            } else {
               throw new NotSoftDeletedException("The user must be soft deleted first!");
            }

        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

    }

    @Override
    public void restoreUser(Long id) {
        Optional<User> deletedUser=userRepo.findById(id);
        if (deletedUser.isEmpty()){
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        if (userRepo.findByUsername(deletedUser.get().getUsername()).isPresent()){
            throw new NameAlreadyTakenException("There is already user with such username.");
        }
        User user= deletedUser.get();
        user.setIsDeleted(false);
        userRepo.save(user);
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDto(optionalUser.get());
    }
}
