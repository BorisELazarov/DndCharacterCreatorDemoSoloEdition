package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.auth.AuthenticationRequest;
import com.example.dnd_character_creator_solo_edition.bll.dtos.auth.AuthenticationResponse;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.LoginCredentials;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.RegisterDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.UserMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.AuthService;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.JwtService;
import com.example.dnd_character_creator_solo_edition.dal.entities.Role;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.UserRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.EmailAlreadyTakenException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NameAlreadyTakenException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder,
                           JwtService jwtService, AuthenticationManager authManager,
                           UserMapper userMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userMapper=userMapper;
    }



    @PostConstruct
    private void seedDataForUsersAndRoles() {
        seedRoles();
        seedUser();
    }

    private void seedRoles() {
        if (roleRepo.count()>0)
            return;
        List<Role> roles = List.of(
                getRole("user"),
                getRole("data manager"),
                getRole("admin")
        );
        roleRepo.saveAll(roles);
    }

    static Role getRole(String title){
        Role role=new Role();
        role.setTitle(title);
        return role;
    }

    private void seedUser() {
        if (userRepo.findByUsername("Boris").isEmpty()) {
            User user = new User();
            user.setUsername("Boris");
            user.setPassword("BorisPass");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail("admin@email.com");
            Optional<Role> role = roleRepo.findByTitle("admin");
            role.ifPresent(user::setRole);
            userRepo.save(user);
        }
    }

    @Override
    public AuthenticationResponse register(RegisterDTO registerDTO) {
        if (userRepo.findByEmail(registerDTO.email()).isPresent()){
            throw new EmailAlreadyTakenException("This email is already taken!");
        }
        if (userRepo.findByUsername(registerDTO.username()).isPresent()) {
            throw new NameAlreadyTakenException("This username is already taken!");
        }

        User user= userMapper.fromDto(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> role=roleRepo.findByTitle("user");
        role.ifPresent(user::setRole);
        user = userRepo.save(user);
        LoginCredentials login=new LoginCredentials();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());
        String jwtToken= jwtService.generateToken(login);
        return new AuthenticationResponse(jwtToken,this.userMapper.toDto(user));
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepo.findByEmail(request.email())
                .orElseThrow(()->new NotFoundException("There is no user with such email!"));

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );

        LoginCredentials login=new LoginCredentials();
        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());

        String jwtToken= jwtService.generateToken(login);
        return new AuthenticationResponse(jwtToken,this.userMapper.toDto(user));
    }
}
