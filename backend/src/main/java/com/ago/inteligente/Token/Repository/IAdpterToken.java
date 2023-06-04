package com.ago.inteligente.Token.Repository;

import com.ago.inteligente.Token.Token;
import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.Utils.Commom.Exception.AgroException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAdpterToken {
    void saveUserToken(UserDto user, String jwtToken) throws AgroException;

    List<Token> findAllValidTokenByUser(UUID id);

    void saveAllToken(List<Token> tokens);

    Optional<Token> findByToken(String token);
}
