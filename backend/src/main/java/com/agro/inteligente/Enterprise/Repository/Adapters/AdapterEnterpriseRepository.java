package com.agro.inteligente.Enterprise.Repository.Adapters;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.Enterprise.Repository.IEnterpriseRepository;
import com.agro.inteligente.User.Domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public List<EnterpriseResponseDto> getMyEnterprise(UUID userId) {
        List<EnterpriseResponseDto> enterpriseResponseDtos = new ArrayList<EnterpriseResponseDto>();

        List<EnterpriseModelRepository> model = this.repository.getMyEnterprise(userId);

        for (int i = 0; i < model.size(); i++) {
            enterpriseResponseDtos.add(model.get(i).toDomain());
        }

        return enterpriseResponseDtos;
    }


}
