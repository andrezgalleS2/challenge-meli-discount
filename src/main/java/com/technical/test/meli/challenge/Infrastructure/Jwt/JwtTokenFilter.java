package com.technical.test.meli.challenge.Infrastructure.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.test.meli.challenge.Application.Dto.TokenResponse.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${basic.auth.username}")
    private String basicAuthUsername;

    @Value("${basic.auth.password}")
    private String basicAuthPassword;

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        Response sendErrorResponse = new Response();
        ObjectMapper objectMapper = new ObjectMapper();

        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/api/token/generate")) {
            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                sendErrorResponse.setCode(401);
                sendErrorResponse.setMessage("Credenciales no encontradas o formato incorrecto");
                response.setStatus(401);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(sendErrorResponse));
                return;
            }

            String base64Credentials = authHeader.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] values = credentials.split(":", 2);

            String username = values[0];
            String password = values[1];

            if (!basicAuthUsername.equals(username) || !basicAuthPassword.equals(password)) {
                sendErrorResponse.setCode(401);
                sendErrorResponse.setMessage("Credenciales inválidas");
                response.setStatus(401);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(sendErrorResponse));
                return;
            }

            chain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse.setCode(401);
            sendErrorResponse.setMessage("Token no encontrado o formato incorrecto");
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(sendErrorResponse));
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
            String username = claims.getSubject();

            if (username != null) {
                UserDetails userDetails = new User(username, "", Collections.emptyList());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            logger.error("Error in doFilterInternal", e);
            sendErrorResponse.setCode(401);
            sendErrorResponse.setMessage("Token inválido");
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(sendErrorResponse));
            return;
        }

        chain.doFilter(request, response);
    }

}
