package com.agro.inteligente.Enterprise.Repository;

import com.agro.inteligente.Enterprise.Domain.EnterpriseQrCodeDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.DateTimeException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Table(name = "enterprise_qrcode_invite")
@Entity
public class EnterpriseQrCodeModelRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  updatable = false, unique = true, nullable = false)
    private UUID id;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private EnterpriseModelRepository enterprise;

    private String url;
    public EnterpriseQrCodeDto toDomain(){
        return EnterpriseQrCodeDto
                .builder()
                .qrcode_id(this.id.toString())
                .expiredAt(this.expiredAt)
                .url(this.url)
                .build();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public EnterpriseModelRepository getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseModelRepository enterpriseQrCode) {
        this.enterprise = enterpriseQrCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
