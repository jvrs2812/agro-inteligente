package com.agro.inteligente.Enterprise.Repository;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name = "enterprise")
@Entity
public class EnterpriseModelRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  updatable = false, unique = true, nullable = false)
    private UUID id;
    @NotNull
    private String name_fancy;
    @NotNull
    private String cnpj;
    @NotNull
    private String adress;
    @NotNull
    private String number;
    @NotNull
    private String district;
    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<UserModelRepository> users;

    public static EnterpriseModelRepository toModel(EnterpriseDto enterpriseDto, List<UserDto> userDto){
        EnterpriseModelRepository enterpriseModelRepository = new EnterpriseModelRepository();
        enterpriseModelRepository.id = UUID.randomUUID();
        enterpriseModelRepository.adress = enterpriseDto.getAdress();
        enterpriseModelRepository.cnpj = enterpriseDto.getCnpj();
        enterpriseModelRepository.district = enterpriseDto.getDistrict();
        enterpriseModelRepository.name_fancy = enterpriseDto.getName_fancy();
        enterpriseModelRepository.number = enterpriseDto.getNumber();

        List<UserModelRepository> userModelRepositories = new ArrayList<UserModelRepository>();

        for (int i = 0; i < userDto.size(); i++) {
            userModelRepositories.add(UserModelRepository.toModel(userDto.get(i)));
        }

        enterpriseModelRepository.users = userModelRepositories;

        return enterpriseModelRepository;
    }

    public static EnterpriseModelRepository toModelDto(EnterpriseResponseDto enterpriseDto){
        EnterpriseModelRepository enterpriseModelRepository = new EnterpriseModelRepository();
        enterpriseModelRepository.id = UUID.fromString(enterpriseDto.getId());
        enterpriseModelRepository.adress = enterpriseDto.getAdress();
        enterpriseModelRepository.cnpj = enterpriseDto.getCnpj();
        enterpriseModelRepository.district = enterpriseDto.getDistrict();
        enterpriseModelRepository.name_fancy = enterpriseDto.getName_fancy();
        enterpriseModelRepository.number = enterpriseDto.getNumber();

        return enterpriseModelRepository;
    }

    public EnterpriseResponseDto toDomain(){
        return new EnterpriseResponseDto(this.name_fancy, this.cnpj, this.adress, this.number, this.district, this.id.toString());
    }

}
