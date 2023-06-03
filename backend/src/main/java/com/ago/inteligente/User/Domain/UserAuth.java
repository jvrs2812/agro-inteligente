package com.ago.inteligente.User.Domain;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;

@Data
@RequiredArgsConstructor
public class UserAuth {

    public UserAuth(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Email
    private String email;

    private String password;
}
