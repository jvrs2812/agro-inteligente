package com.agro.inteligente.Token.Repository;

import com.agro.inteligente.Token.Token;
import com.agro.inteligente.Token.TokenType;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.agro.inteligente.Token.Exception.TokenExceptionEnum.TOKEN_MULTIPLE_GENERATE;

@Service
@RequiredArgsConstructor
public class AdpterToken implements IAdpterToken{

    private final TokenRepository repository;
    @Override
    public void saveUserToken(UserDto user, String jwtToken) throws AgroException {
        Token token = Token.builder()
                .user(UserModelRepository.toModel(user))
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .id(UUID.randomUUID())
                .build();

        try{
            this.repository.save(token);
        }catch (DataIntegrityViolationException e){
            throw new AgroException(TOKEN_MULTIPLE_GENERATE);
        }

    }

    @Override
    public List<Token> findAllValidTokenByUser(UUID id) {
        return this.repository.findAllValidTokenByUser(id);
    }

    @Override
    public void saveAllToken(List<Token> tokens) {
        this.repository.saveAll(tokens);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return this.repository.findByToken(token);
    }
}
