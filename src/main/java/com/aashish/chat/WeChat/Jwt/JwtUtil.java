package com.aashish.chat.WeChat.Jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import com.aashish.chat.WeChat.Domain.MyUser;
@Component
public class JwtUtil {

    @Value("${spring.app.jwtSecret}")
    private String secretKey;


    public String generateToken(MyUser user){
        String jwtToken = null;

        Key key=Keys.hmacShaKeyFor(secretKey.getBytes());

        jwtToken = Jwts.builder()
        .subject(user.getId())
        .issuedAt(new Date())
        .signWith(key)
        .compact();

        return jwtToken;    
    }

    public Claims validaateToken(String token){
   

   
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
    
        return Jwts.parser()
        .verifyWith((SecretKey) key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
        }
        
    
}
