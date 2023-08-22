package com.agro.inteligente.Enterprise.Domain;

import lombok.Data;

@Data
public class EnterpriseResponseDto extends EnterpriseDto{
    public EnterpriseResponseDto(String name_fancy, String cnpj, String adress, String number, String district, String id) {
        super(name_fancy, cnpj, adress, number, district);
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
