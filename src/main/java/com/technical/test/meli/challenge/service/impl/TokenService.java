package com.technical.test.meli.challenge.service.impl;

import com.technical.test.meli.challenge.dto.response.TokenResponse;
import com.technical.test.meli.challenge.security.JwtTokenProvider;
import com.technical.test.meli.challenge.service.IToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService implements IToken {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${jwt.username}")
    private String username;

    @Value("${jwt.expiration}")
    private String validityInMilliseconds;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponse generateToken() {

        TokenResponse tokenResponse = new TokenResponse();

        try {

            tokenResponse.setCode(HttpServletResponse.SC_OK);
            tokenResponse.setToken(jwtTokenProvider.createToken(username));
            tokenResponse.setExpirationIn(validityInMilliseconds);

        } catch (Exception e) {

            logger.error("Error in generateToken", e);
            tokenResponse.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            tokenResponse.setToken(null);
            tokenResponse.setExpirationIn(null);

        }

        return tokenResponse;
    }
}