package com.example.Project.UserRegistration.Security;

import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import com.example.Project.UserRegistration.Model.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    //secret key generation
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final int jwtExpirationMs = 3600000;

    private UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Generate Token
    public String generateToken(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Set<Role> roles = user.get().getRoles();

        //Add roles to the token
        return Jwts.builder().setSubject(email).claim("roles", roles.stream()
                        .map(role -> role.getName()).collect(Collectors.joining(",")))
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(secretKey).compact();
    }

    //Extract Email
    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    //Extract Roles
    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parserBuilder().setSigningKey(secretKey).
                build().parseClaimsJws(token).getBody().get("roles", String.class);
        return Set.of(rolesString);
    }

    //Validating token
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
