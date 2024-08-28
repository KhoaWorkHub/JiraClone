package com.khoa.managementsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

   static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());


    public static String generateToken(Authentication auth) {


        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JwtConstant.EXPIRATION_TIME))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();

        return jwt;

    }

    public static String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return String.valueOf(claims.get("email"));
    }

    public static String getIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return String.valueOf(claims.get("id"));
    }
}
