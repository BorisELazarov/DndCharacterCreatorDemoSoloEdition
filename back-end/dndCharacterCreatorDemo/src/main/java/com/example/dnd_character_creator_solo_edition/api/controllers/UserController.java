package com.example.dnd_character_creator_solo_edition.api.controllers;

import com.example.dnd_character_creator_solo_edition.bll.dtos.users.SearchUserDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestBody SearchUserDTO searchUserDTO
            )
    {
        return new ResponseEntity<>(
                userService.getUsers(searchUserDTO),
                HttpStatus.OK
        );
    }

    @PutMapping(path="/restore/{id}")
    public ResponseEntity<Void> restoreAccount(@PathVariable("id") Long id){
        userService.restoreUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long id){
        return new ResponseEntity<>(
                userService.getUser(id),
                HttpStatus.OK
        );
    }

    @PutMapping(path="changeUsername/{userId}/{username}")
    public ResponseEntity<Void> changeUsername(
            @PathVariable("userId") Long id,
            @PathVariable("username") String username){
        userService.changeUsername(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="changePassword/{userId}/{oldPassword}/{newPassword}")
    public ResponseEntity<Void> changePassword(
            @PathVariable("userId") Long id,
            @PathVariable("oldPassword") String oldPassword,
            @PathVariable("newPassword") String newPassword){
        userService.changePassword(id,oldPassword,newPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "changeEmail/{id}/{email}")
    public ResponseEntity<Void> changeEmail(
            @PathVariable("id") Long id,
            @PathVariable("email") String email
    ){
        userService.changeEmail(id,email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "changeRole/{id}/{role}")
    public ResponseEntity<Void> changeRole(
            @PathVariable("id") Long id,
            @PathVariable("role") String role
    ){
        userService.changeRole(id,role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id) {
         userService.softDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/confirmed/{userId}")
    public ResponseEntity<Void> hardDeleteUser(@PathVariable("userId") Long id){
        userService.hardDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
