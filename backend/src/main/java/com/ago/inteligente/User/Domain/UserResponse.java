package com.ago.inteligente.User.Domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Data
@RequiredArgsConstructor
public class UserResponse {

    public UserResponse(String access_token, String refresh_token, BigInteger timeout){
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.timeout = timeout;
    }

    private String access_token;
    private String refresh_token;
    private BigInteger timeout;
}
