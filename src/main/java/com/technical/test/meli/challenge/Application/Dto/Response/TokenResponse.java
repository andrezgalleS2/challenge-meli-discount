package com.technical.test.meli.challenge.Application.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private Number code;
    private String token;
    private String expirationIn;
}
