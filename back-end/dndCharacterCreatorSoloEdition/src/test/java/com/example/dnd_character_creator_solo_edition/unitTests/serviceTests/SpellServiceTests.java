package com.example.dnd_character_creator_solo_edition.unitTests.serviceTests;

import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SearchSpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.spells.SpellDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.SpellMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.implementations.SpellServiceImpl;
import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.dal.entities.BaseEntity;
import com.example.dnd_character_creator_solo_edition.dal.entities.Spell;
import com.example.dnd_character_creator_solo_edition.dal.repos.SpellRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotSoftDeletedException;
import com.example.dnd_character_creator_solo_edition.filters.SpellFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpellServiceTests {
    @Mock
    private SpellMapper mapper;
    @Mock
    private SpellRepo repo;

    @InjectMocks
    private SpellServiceImpl service;

    private Spell spell;
    private SpellDTO spellDTO;
    private Spell createSpell;
    private SpellDTO createSpellDTO;
    private List<Spell> spells;
    private List<SpellDTO> spellDTOS;

    private Spell getSpell(Long id, boolean isDeleted,String name, int level,
                           String castingTime, int castingRange,
                           String target, String components,
                           int duration, String description){
        Spell item=new Spell();
        item.setId(id);
        item.setIsDeleted(isDeleted);
        item.setName(name);
        item.setDescription(description);
        item.setLevel(level);
        item.setCastingRange(castingRange);
        item.setTarget(target);
        item.setComponents(components);
        item.setDuration(duration);
        item.setCastingTime(castingTime);
        return item;
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        spell = getSpell(1L, false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder");
        spellDTO = new SpellDTO(Optional.of(1L), false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder"
        );
        createSpell = getSpell(null, false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder");
        createSpellDTO = new SpellDTO(Optional.empty(), false, "Thunder",
                1, "A",
                20, "asfd",
                "V, M", 0,
                "Thunder"
        );
        spells =List.of(
                spell,
                getSpell(2L, false, "Thunder",
                        2, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Fire"),
                getSpell(3L, true, "Thunder",
                3, "A",
                20, "asfd",
                "V, M", 0,
                "Frost"),
                getSpell(3L, true, "Thunder",
                3, "A",
                20, "asfd",
                "V, M", 0,
                "Force")
        );
        spellDTOS = List.of(
                spellDTO,
                new SpellDTO(Optional.of(2L), false, "Thunder",
                        2, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Fire"),
                new SpellDTO(Optional.of(3L), true, "Thunder",
                        3, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Frost"),
                new SpellDTO(Optional.of(4L), true, "Thunder",
                        4, "A",
                        20, "asfd",
                        "V, M", 0,
                        "Force")
        );
        Mockito.when(mapper.toDto(spell)).thenReturn(spellDTO);
        Mockito.when(mapper.fromDto(spellDTO)).thenReturn(spell);
    }

    @Test
    void getProficiencyNotNull(){
        Mockito.when(repo.findById(1L)).thenReturn(
                Optional.of(spell)
        );
        SpellDTO dto=service.getSpell(1L);
        assertNotNull(dto);
    }

    @Test
    void getDeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(true)).thenReturn(
                spells.stream().filter(BaseEntity::getIsDeleted).toList()
        );
        Mockito.when(
                mapper.toDTOs(spells.stream().filter(BaseEntity::getIsDeleted).toList())
        ).thenReturn(
                spellDTOS.stream().filter(SpellDTO::isDeleted).toList()
        );
        List<SpellDTO> dtos=service.getSpells(true,
                new SearchSpellDTO(
                        new SpellFilter("",Optional.empty(),"", Optional.empty()),
                        new Sort("",true)
                )
        );
        assertFalse(dtos.isEmpty());
    }

    @Test
    void getUndeletedProficienciesNotEmpty(){
        Mockito.when(repo.findAll(false)).thenReturn(
                spells.stream().filter(x->!x.getIsDeleted()).toList()
        );
        Mockito.when(
                mapper.toDTOs(spells.stream().filter(x->!x.getIsDeleted()).toList())
        ).thenReturn(
                spellDTOS.stream().filter(x->!x.isDeleted()).toList()
        );
        List<SpellDTO> dtos=service.getSpells(false,
                new SearchSpellDTO(
                        new SpellFilter("",Optional.empty(),"", Optional.empty()),
                        new Sort("",true)
                )
        );
        assertFalse(dtos.isEmpty());
    }

    @Test
    void addSpellReturnAreEqual(){
        Mockito.when(repo.save(createSpell)).thenReturn(spell);
        Mockito.when(mapper.fromDto(createSpellDTO)).thenReturn(createSpell);
        SpellDTO mockDTO=service.addSpell(createSpellDTO);
        assertEquals(mockDTO,spellDTO);
    }

    @Test
    void editSpellReturnAreEqual(){
        spellDTO.id().ifPresent(id->Mockito.when(repo.existsById(id)).thenReturn(true));
        Mockito.when(repo.save(mapper.fromDto(spellDTO))).thenReturn(spell);
        assertNotNull(repo.save(mapper.fromDto(spellDTO)));
        assertEquals(service.editSpell(spellDTO),spellDTO);
    }

    @Test
    void softDeleteSpellThrowNotFoundException(){
        Mockito.doThrow(new NotFoundException("")).when(repo).findById(0L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(spell));
        assertThrows(NotFoundException.class,
                ()->service.softDeleteSpell(0L));
        assertDoesNotThrow(()->service.softDeleteSpell(1L));
    }

    @Test
    void hardDeleteSpellThrowNotFoundException(){
        Mockito.doThrow(new NotFoundException("")).when(repo).findById(0L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(spell));
        assertThrows(NotFoundException.class,
                ()->service.hardDeleteSpell(0L));
        assertThrows(NotSoftDeletedException.class,()->service.hardDeleteSpell(1L));
    }
}
