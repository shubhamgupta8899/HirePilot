package com.shubham.HirePilot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.core.instrument.config.validate.Validated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretkey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(UserDetails userDetails){

        return buildToken(userDetails, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails){

        return buildToken(userDetails, refreshTokenExpiration);
    }

    private String buildToken(UserDetails userDetails, long expiration){

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token){

        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenValid(String token, UserDetails userDetails){

        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){

        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){


        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = secretkey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
