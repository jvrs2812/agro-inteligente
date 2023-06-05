package com.agro.inteligente.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseUtils {

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String password){
        return this.passwordEncoder.encode(password);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword){
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
