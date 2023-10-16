package com.agro.inteligente.User.Domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private String access_token;
    private String refresh_token;
    private int expiredAt;
    private LocalDateTime dateGenerate;
    private String name;
}
