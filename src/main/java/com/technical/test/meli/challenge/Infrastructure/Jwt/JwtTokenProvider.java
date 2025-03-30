package com.technical.test.meli.challenge.Infrastructure.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    public String createToken(String username) {

        try {
            Date now = new Date();
            Date validity = new Date(now.getTime() + validityInMilliseconds);

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();

        } catch (Exception e) {
            logger.error("Error in createToken", e);
            return null;
        }
    }


    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


}