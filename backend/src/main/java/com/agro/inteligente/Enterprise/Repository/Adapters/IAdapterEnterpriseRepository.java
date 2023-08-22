package com.agro.inteligente.Enterprise.Repository.Adapters;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.User.Domain.UserDto;

import java.util.List;
import java.util.UUID;

public interface IAdapterEnterpriseRepository {
    void CreateEnterprise(EnterpriseDto dto, UserDto user);

    boolean existCnpj(String cnpj);

    List<EnterpriseResponseDto> getMyEnterprise(UUID userId);

}
