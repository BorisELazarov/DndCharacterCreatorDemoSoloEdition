package com.example.dnd_character_creator_solo_edition.bll.services.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * @author boriselazarov@gmail
 */
public interface JwtService {

    String extractEmail(String token);

    boolean isTokenValid(String token, UserDetails login);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    Claims extractAllClaims(String token);

    Key getSignInKey();
}
