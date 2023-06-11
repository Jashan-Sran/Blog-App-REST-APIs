package com.geggitech.springboot.security;

import com.geggitech.springboot.exception.ResourceFoundInDataBase;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-milliseconds}")
    private long expirationDate;

//    generate jwt token

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();

        Date ExpirationDate = new Date(currentDate.getTime() + expirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(ExpirationDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

//    get username from jwt token

    public String getUsername(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

//    Validate jwt token

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ResourceFoundInDataBase("Invalid JWT token", HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException ex) {
            throw new ResourceFoundInDataBase("Expired JWT token", HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException ex) {
            throw new ResourceFoundInDataBase("Unsupported JWT token", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            throw new ResourceFoundInDataBase("JWT claims string is empty.", HttpStatus.BAD_REQUEST);
        }
    }
}
