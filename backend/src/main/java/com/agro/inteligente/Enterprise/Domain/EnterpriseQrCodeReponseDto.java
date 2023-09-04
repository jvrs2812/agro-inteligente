package com.agro.inteligente.Enterprise.Domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EnterpriseQrCodeReponseDto {

    public String url;

    public Date expired_at;
}
