package com.example.dnd_character_creator_solo_edition.unitTests.serviceTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.SearchCharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.UserDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.CharacterMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.implementations.CharacterServiceImpl;
import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.dal.entities.*;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.repos.CharacterRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import com.example.dnd_character_creator_solo_edition.filters.CharacterFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CharacterServiceTests {
    @Mock
    private CharacterMapper mapper;
    @Mock
    private CharacterRepo repo;
    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private CharacterServiceImpl service;

    private Role role;
    private Character character;
    private CharacterDTO characterDTO;
    private Character createCharacter;
    private CharacterDTO createCharacterDTO;
    private List<Character> characters;
    private List<CharacterDTO> characterDTOS;

    private Character getCharacter(Long id, boolean isDeleted, String name,
                                   byte level, byte baseStr,
                                   byte baseDex, byte baseCon,
                                   byte baseInt, byte baseWis,
                                   byte baseCha){
        Character item=new Character();
        item.setId(id);
        item.setIsDeleted(isDeleted);
        item.setName(name);
        item.setLevel(level);
        item.setBaseStr(baseStr);
        item.setBaseDex(baseDex);
        item.setBaseCon(baseCon);
        item.setBaseInt(baseInt);
        item.setBaseWis(baseWis);
        item.setBaseCha(baseCha);
        item.setUser(new User());
        item.setDNDclass(new DNDclass());
        return item;
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        role=Mockito.mock(Role.class);
        UserDTO userDTO=Mockito.mock(UserDTO.class);
        ClassDTO classDTO=Mockito.mock(ClassDTO.class);
        character = getCharacter(1L,true, "Norman", (byte)20,
                (byte)20, (byte)20, (byte) 20,
                (byte) 20, (byte) 20, (byte) 20);
        characterDTO = new CharacterDTO(Optional.of(1L),true, "Norman",
                userDTO, classDTO, (byte)20,
                (byte)20, (byte)20, (byte) 20,
                (byte) 20, (byte) 20, (byte) 20,
                Set.of(), Set.of());
        createCharacter = getCharacter(null,true, "Norman", (byte)20,
                (byte)20, (byte)20, (byte) 20,
                (byte) 20, (byte) 20, (byte) 20);
        createCharacterDTO = new CharacterDTO(Optional.empty(),true, "Norman",
                userDTO, classDTO, (byte)20,
                (byte)20, (byte)20, (byte) 20,
                (byte) 20, (byte) 20, (byte) 20,
                Set.of(), Set.of());
        characters =List.of(
                character,
                getCharacter(2L,false, "Norman", (byte)10,
                        (byte)12, (byte)12, (byte) 12,
                        (byte) 12, (byte) 12, (byte) 12),
                getCharacter(3L,false, "Gordan", (byte)12,
                        (byte)20, (byte)20, (byte) 20,
                        (byte) 20, (byte) 20, (byte) 20),
                getCharacter(4L,true, "Jordan", (byte)14,
                        (byte)20, (byte)20, (byte) 20,
                        (byte) 20, (byte) 20, (byte) 20)
        );
        characterDTOS = List.of(
                characterDTO,
                new CharacterDTO(Optional.of(2L),false, "Gordan",
                        userDTO, classDTO, (byte)10,
                        (byte)12, (byte)12, (byte) 12,
                        (byte) 12, (byte) 12, (byte) 12,
                        Set.of(), Set.of()),
                new CharacterDTO(Optional.of(3L),false, "Jordan",
                        userDTO, classDTO, (byte)14,
                        (byte)20, (byte)20, (byte) 20,
                        (byte) 20, (byte) 20, (byte) 20,
                        Set.of(), Set.of()),
                new CharacterDTO(Optional.of(4L),true, "Morgan",
                        userDTO, classDTO, (byte)12,
                        (byte)20, (byte)20, (byte) 20,
                        (byte) 20, (byte) 20, (byte) 20,
                        Set.of(), Set.of())
        );
        Mockito.when(mapper.toDto(character)).thenReturn(characterDTO);
        Mockito.when(mapper.fromDto(characterDTO,Optional.of(role))).thenReturn(character);
    }

    @Test
    void getProficiencyNotNull(){
        Mockito.when(repo.findById(1L)).thenReturn(
                Optional.of(character)
        );
        Mockito.when(service.getCharacterById(1L)).thenReturn(characterDTO);
        CharacterDTO dto=service.getCharacterById(1L);
        assertNotNull(dto);
    }

    @Test
    void getDeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(true)).thenReturn(
                characters.stream().filter(BaseEntity::getIsDeleted).toList()
        );
        Mockito.when(
                mapper.toDTOs(characters.stream().filter(BaseEntity::getIsDeleted).toList())
        ).thenReturn(
                characterDTOS.stream().filter(CharacterDTO::isDeleted).toList()
        );
        List<CharacterDTO> dtos=service.getCharacters(1L,true,
                new SearchCharacterDTO(
                        new CharacterFilter("",Optional.empty(),""),
                        new Sort("",true)
                        ));
        assertFalse(dtos.isEmpty());
    }

    @Test
    void getUndeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(false)).thenReturn(
                characters.stream().filter(x->!x.getIsDeleted()).toList()
        );
        Mockito.when(
                mapper.toDTOs(characters.stream().filter(x->!x.getIsDeleted()).toList())
        ).thenReturn(
                characterDTOS.stream().filter(x->!x.isDeleted()).toList()
        );
        List<CharacterDTO> dtos=service.getCharacters(1L,false,
                new SearchCharacterDTO(
                        new CharacterFilter("",Optional.empty(),""),
                        new Sort("",true)
                ));
        assertFalse(dtos.isEmpty());
    }

    @Test
    void addCharacterReturnAreEqual(){
        Mockito.when(repo.findByName(createCharacterDTO.name())).thenReturn(Optional.empty());
        Mockito.when(
                repo.save(
                        mapper.fromDto(
                                characterDTO,
                                roleRepo.findByTitle(characterDTO.user().role())
                        )
                )
        ).thenReturn(character);
        Mockito.when(mapper.fromDto(createCharacterDTO, Optional.of(role))).thenReturn(createCharacter);
        CharacterDTO dto=service.addCharacter(createCharacterDTO);
        assertEquals(dto,characterDTO);
    }

    @Test
    void editCharacterReturnAreEqual(){
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(character));
        Mockito.when(repo.findByName(characterDTO.name())).thenReturn(Optional.empty());
        Mockito.when(repo.save(mapper.fromDto(characterDTO, Optional.of(role)))).thenReturn(character);
        assertNotNull(repo.save(mapper.fromDto(characterDTO, Optional.of(role))));
        assertEquals(service.editCharacter(characterDTO),characterDTO);
    }

    @Test
    void softDeleteClassThrowNotFoundException(){
        Mockito.doThrow(new NotFoundException("")).when(repo).findById(0L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(character));
        assertThrows(NotFoundException.class,
                ()->service.softDeleteCharacter(0L));
        assertDoesNotThrow(()->service.softDeleteCharacter(1L));
    }

    @Test
    void hardDeleteClassThrowNotFoundException(){
        Mockito.doThrow(new NotFoundException("")).when(repo).findById(0L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(character));
        assertThrows(NotFoundException.class,
                ()->service.hardDeleteCharacter(0L));
        assertDoesNotThrow(()->service.hardDeleteCharacter(1L));
    }
}