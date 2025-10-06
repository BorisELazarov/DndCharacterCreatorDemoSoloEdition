package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.JwtService;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.dal.repos.UserRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String KEY
            ="K7aToyGnmyxFF+GHzMOsArYMibOqtWn3J9DxJGN2CczFIwc12/dAty4eV5tRtvPeLEC2cSiRtrtGBHuET8buSQ==";
    private final UserRepo userRepo;

    public JwtServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String extractEmail(String token) {
        String username = extractClaim(token,Claims::getSubject);
        Optional<User> user=this.userRepo.findByEmail(username);
        if (user.isPresent()){
            return user.get().getEmail();
        }
        throw new NotFoundException("There is no such user!");
    }

    @Override
    public boolean isTokenValid(String token, UserDetails login){
        final String email= extractEmail(token);
        return email.equals(login.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails){
        return generateToken(
                Map.of(),
        userDetails);
    }

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
