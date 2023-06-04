package com.ago.inteligente.User.Domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
public class UserResponse {
    private String access_token;
    private String refresh_token;
    private int expiredAt;
}
