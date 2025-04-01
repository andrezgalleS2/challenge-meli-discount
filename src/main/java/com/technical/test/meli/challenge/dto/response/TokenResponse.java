package com.technical.test.meli.challenge.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private Number code;
    private String token;
    private String expirationIn;
}
