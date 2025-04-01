package com.technical.test.meli.challenge.controller;

import com.technical.test.meli.challenge.dto.response.TokenResponse;
import com.technical.test.meli.challenge.service.IToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final IToken tokenService;

    @GetMapping(value = "/generate")
    public ResponseEntity<TokenResponse> generate(){
        return ResponseEntity.ok(tokenService.generateToken());
    }
}