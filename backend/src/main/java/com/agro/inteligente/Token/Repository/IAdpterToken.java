package com.agro.inteligente.Token.Repository;

import com.agro.inteligente.Token.Token;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAdpterToken {
    void saveUserToken(UserDto user, String jwtToken) throws AgroException;

    List<Token> findAllValidTokenByUser(UUID id);

    void saveAllToken(List<Token> tokens);

    Optional<Token> findByToken(String token);
}
