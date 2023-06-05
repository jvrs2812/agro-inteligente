package com.agro.inteligente.Configuration.Security;

import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Repository.UserModelRepository;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJwtService {
    String generateToken(UserDto userDto);

    int gerarTimeout(int timeoutMin);

    String generateRefreshToken(UserDto userDto);

    boolean isTokenValid(String token, UserDetails user);


    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String extractUsername(String token);
    String getIdUserContextSecurity();

    UserModelRepository getUserContextSecurity();
}
