package com.agro.inteligente.Enterprise.Repository.Adapters;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.Enterprise.Repository.IEnterpriseRepository;
import com.agro.inteligente.User.Domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdapterEnterpriseRepository implements IAdapterEnterpriseRepository{

    private final IEnterpriseRepository repository;

    @Override
    public void CreateEnterprise(EnterpriseDto dto, UserDto user) {
        List<UserDto> list = new ArrayList<UserDto>();
        list.add(user);
        this.repository.save(EnterpriseModelRepository.toModel(dto, list));
    }

    @Override
    public boolean existCnpj(String cnpj) {
        return this.repository.existByCnpj(cnpj);
    }
}
