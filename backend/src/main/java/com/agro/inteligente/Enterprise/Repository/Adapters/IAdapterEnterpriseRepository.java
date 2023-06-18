package com.agro.inteligente.Enterprise.Repository.Adapters;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.User.Domain.UserDto;

public interface IAdapterEnterpriseRepository {
    void CreateEnterprise(EnterpriseDto dto, UserDto user);

    boolean existCnpj(String cnpj);

}
