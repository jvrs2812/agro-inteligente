package com.ago.inteligente.Configuration.Security;

import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.User.Repository.UserModelRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
