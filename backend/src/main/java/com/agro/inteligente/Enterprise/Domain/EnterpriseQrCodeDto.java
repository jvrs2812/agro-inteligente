package com.agro.inteligente.Enterprise.Domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EnterpriseQrCodeDto {

    private String qrcode_id;

    private Date expiredAt;

    private String url;
}
