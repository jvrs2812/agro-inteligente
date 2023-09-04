package com.agro.inteligente.Enterprise.Domain;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class EnterpriseDto {

    public EnterpriseDto(String name_fancy, String cnpj, String adress, String number, String district) {
        this.name_fancy = name_fancy;
        this.cnpj = cnpj;
        this.adress = adress;
        this.number = number;
        this.district = district;
    }

    @NotEmpty
    private String name_fancy;

    @NotEmpty
    private String cnpj;

    @NotEmpty
    private String adress;

    @NotEmpty
    private String number;

    @NotEmpty
    private String district;

    public String getName_fancy() {
        return name_fancy;
    }

    public void setName_fancy(String name_fancy) {
        this.name_fancy = name_fancy;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
