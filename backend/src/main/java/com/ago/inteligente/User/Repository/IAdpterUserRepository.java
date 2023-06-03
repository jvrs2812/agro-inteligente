package com.ago.inteligente.User.Repository;

import com.ago.inteligente.User.Domain.UserRegisterDto;

public interface IAdpterUserRepository {
    void save(UserRegisterDto register);
}
