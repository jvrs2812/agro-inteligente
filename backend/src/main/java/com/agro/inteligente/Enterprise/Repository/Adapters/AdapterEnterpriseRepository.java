package com.agro.inteligente.Enterprise.Repository.Adapters;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseQrCodeDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseQrCodeReponseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.Enterprise.Repository.EnterpriseQrCodeModelRepository;
import com.agro.inteligente.Enterprise.Repository.IEnterpriseRepository;
import com.agro.inteligente.Enterprise.Repository.IEnterpriseRepositoryQrcode;
import com.agro.inteligente.User.Domain.UserDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdapterEnterpriseRepository implements IAdapterEnterpriseRepository{

    private final IEnterpriseRepository repository;

    private final IEnterpriseRepositoryQrcode repositoryQrcode;

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

    @Override
    public EnterpriseQrCodeDto CreateQrCode(EnterpriseResponseDto enterprise) {

        LocalDateTime dateActually = LocalDateTime.now();

        LocalDateTime dateExpirantion = dateActually.plusMinutes(15);

        EnterpriseModelRepository enterpriseModelRepository = new EnterpriseModelRepository();

        EnterpriseQrCodeModelRepository model = new EnterpriseQrCodeModelRepository();
        model.setEnterprise(EnterpriseModelRepository.toModelDto(enterprise));
        model.setExpiredAt(Date.from(dateExpirantion.atZone(ZoneId.systemDefault()).toInstant()));
        model.setUrl("");

        return this.repositoryQrcode.save(model).toDomain();
    }

    @Override
    public boolean existEntepriseForThisUser(UUID user_id, UUID enteprise_id) {
        return this.repository.existEnterpriseForThisUser(user_id, enteprise_id);
    }

    @Override
    public EnterpriseResponseDto getMyEnterprise(UUID user, UUID enterprise_id) {
        return this.repository.getMyEnterprise(user, enterprise_id).toDomain();
    }

    @Override
    public void updateUrlQrCode(String url, UUID idqrcode) {
        this.repositoryQrcode.updateUrl(url, idqrcode);
    }

    @Override
    public List<EnterpriseQrCodeDto> getAllExpired() {

        List<EnterpriseQrCodeModelRepository> models = this.repositoryQrcode.getAllExpired(new Date());

        List<EnterpriseQrCodeDto> dtos = new ArrayList<>();

        for (int i = 0; i < models.size(); i++) {
            dtos.add(models.get(i).toDomain());
        }

        return dtos;
    }

    @Override
    public void deleteQrCode(UUID id) {
        this.repositoryQrcode.deleteById(id);
    }


}
