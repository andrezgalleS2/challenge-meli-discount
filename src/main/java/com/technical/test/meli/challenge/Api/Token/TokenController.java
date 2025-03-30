package com.technical.test.meli.challenge.Api.Token;

import com.technical.test.meli.challenge.Application.Dto.Response.TokenResponse;
import com.technical.test.meli.challenge.Application.Usescase.Token.IToken;
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