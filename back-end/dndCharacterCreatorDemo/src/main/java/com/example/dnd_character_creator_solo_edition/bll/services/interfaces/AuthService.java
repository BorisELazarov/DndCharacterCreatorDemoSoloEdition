package com.example.dnd_character_creator_solo_edition.bll.services.interfaces;

import com.example.dnd_character_creator_solo_edition.bll.dtos.auth.AuthenticationRequest;
import com.example.dnd_character_creator_solo_edition.bll.dtos.auth.AuthenticationResponse;
import com.example.dnd_character_creator_solo_edition.bll.dtos.users.RegisterDTO;

public interface AuthService {

    AuthenticationResponse register(RegisterDTO registerDTO);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
