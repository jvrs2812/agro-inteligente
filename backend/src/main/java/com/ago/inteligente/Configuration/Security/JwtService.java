package com.ago.inteligente.Configuration.Security;

import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.User.Repository.UserModelRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.function.Function;
@Service
public class JwtService implements IJwtService{

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private int jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private int refreshExpiration;


    public String generateToken(UserDto userDto){

        Map<String, Object> claims = new HashMap<>();
        claims.put("id_user", userDto.getId());

        return generateToken(claims, userDto, this.jwtExpiration);
    }

    @Override
    public int gerarTimeout(int timeoutMin) {
        return (timeoutMin * 60 * 1000);
    }

    @Override
    public String generateRefreshToken(UserDto userDto) {
        return this.generateToken(new HashMap<>(), userDto, this.refreshExpiration);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDto userDto, int timeout){
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + gerarTimeout(timeout);


        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDto.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getIdUserContextSecurity() {
        return ((UserModelRepository) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();
    }


    public UserModelRepository getUserContextSecurity(){
        return ((UserModelRepository) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
