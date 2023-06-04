package com.ago.inteligente.User.UseCases;

import com.ago.inteligente.Configuration.Security.IJwtService;
import com.ago.inteligente.Token.Repository.IAdpterToken;
import com.ago.inteligente.User.Domain.*;
import com.ago.inteligente.User.Repository.IAdpterUserRepository;
import com.ago.inteligente.User.Repository.UserModelRepository;
import com.ago.inteligente.Utils.CaseUtils;
import com.ago.inteligente.Utils.Commom.Exception.AgroException;
import com.ago.inteligente.Utils.Commom.IValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.ago.inteligente.User.Exception.UserExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class Authentication {

    private final IAdpterUserRepository repository;

    private final CaseUtils utils;

    private final IValidation validation;

    private final IJwtService jwtService;

    private final IAdpterToken tokenRepository;

    private final AuthenticationManager authenticationManager;

    @Value("${application.security.jwt.expiration}")
    private int jwtExpiration;


    public UserResponse Register(UserRegisterDto register) throws AgroException {

        if(this.repository.existEmail(register.getEmail()))
            throw new AgroException(EMAIL_EXIST);

        if(this.repository.existCpf(register.getCpf()))
            throw new AgroException(CPF_EXIST);

        if(!this.validation.isValidCpf(register.getCpf()))
            throw new AgroException(CPF_NOT_VALID);

        register.setPassword(this.utils.encodePassword(register.getPassword()));

        UserDto userSaved = this.repository.save(register);

        String jwtToken = this.jwtService.generateToken(userSaved);
        String refreshToken = this.jwtService.generateRefreshToken(userSaved);
        this.tokenRepository.saveUserToken(userSaved, jwtToken);

        return UserResponse.builder()
                .access_token(jwtToken)
                .refresh_token(refreshToken)
                .expiredAt(this.jwtService.gerarTimeout(jwtExpiration))
                .build();
    }

    public UserResponse Login(UserAuth auth) throws AgroException {

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getEmail(),
                        auth.getPassword()
                )
        );

        Optional<UserDto> dto = this.repository.findByEmail(auth.getEmail());

        if(dto.isEmpty())
            throw new AgroException(EMAIL_OR_PASSWORD_DONT_EXIST);

        UserDto user = dto.get();

        if(!this.utils.matchPassword(auth.getPassword(), user.getPassword()))
            throw new AgroException(EMAIL_OR_PASSWORD_DONT_EXIST);

        String jwtToken = this.jwtService.generateToken(user);
        String refreshToken = this.jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        this.tokenRepository.saveUserToken(user, jwtToken);

        return UserResponse
                .builder()
                .expiredAt(this.jwtService.gerarTimeout(jwtExpiration))
                .access_token(this.jwtService.generateToken(user))
                .refresh_token(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(UserDto user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAllToken(validUserTokens);
    }

    public UserResponse refreshToken(
            UserRefreshToken refreshToken
    ) throws AgroException {
        String userEmail = this.jwtService.extractUsername(refreshToken.getRefreshtoken());

        if(userEmail == null)
            throw new AgroException(REFRESH_TOKE_WITH_EMAIL_INVALID);

        Optional<UserDto> userDto = this.repository.findByEmail(userEmail);

        if(userDto.isEmpty())
            throw new AgroException(REFRESH_TOKE_WITH_EMAIL_INVALID);

        UserDto userLogged = userDto.get();

        if(!this.jwtService.isTokenValid(refreshToken.getRefreshtoken(), UserModelRepository.toModel(userLogged)))
            throw new AgroException(REFRESH_TOKEN_INVALIDO);

        String accesToken = jwtService.generateToken(userLogged);
        revokeAllUserTokens(userLogged);
        this.tokenRepository.saveUserToken(userLogged, accesToken);
        return UserResponse.builder()
                .refresh_token(refreshToken.getRefreshtoken())
                .expiredAt(this.jwtService.gerarTimeout(jwtExpiration))
                .access_token(accesToken)
                .build();

    }

}
