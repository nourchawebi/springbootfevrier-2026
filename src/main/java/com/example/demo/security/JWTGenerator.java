package com.example.demo.security;


import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
   private final UserRepo userRepo;

    public JWTGenerator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    private static final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate= new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        // extract roles
        // déclare une variable 'authorities' de type collection qui peut contenir des objets de type 'GrantedAuthority'
        //Le type '?' signifie qu'on accepte n'importe quel type qui étend 'GrantedAuthority'
        Collection<?extends GrantedAuthority> authorities = authentication.getAuthorities();
        // authentication.getAuthorities appelle la méthode getAuthorities de l'objet authentication
        // qui est un objet représentant l'utilisateur actuellement authentifié dans l'application
        List<String> roles = authorities.stream().map(GrantedAuthority :: getAuthority).collect(Collectors.toList());
        UserEntity user = userRepo.findByUsername(username).get();
        String token = Jwts.builder()
                .setSubject(username)
                .claim("user", Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "email",user.getEmail()
                ))
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        System.out.println("token: " + token);
        return token;
    }
    public String getUsernameFromJwt(String token) {
        //Utilisation de parserBuilder pour créer un constructeur de parser JWT
        // cela nous permet de pesonnaliser la configuration pour analyser le token jwt existant
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validationToken(String token) {
      try{  Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return true;}catch (Exception e){
          throw new AuthenticationCredentialsNotFoundException("JWT wasd expired or incorret", e.fillInStackTrace());
      }
    }
}
