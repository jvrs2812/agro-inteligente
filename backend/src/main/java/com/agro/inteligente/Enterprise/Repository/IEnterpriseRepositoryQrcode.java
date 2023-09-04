package com.agro.inteligente.Enterprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEnterpriseRepositoryQrcode extends JpaRepository<EnterpriseQrCodeModelRepository, UUID> {
    @Query(value = "update from enterprise_qrcode_invite set url = :url where id =:idqrcode", nativeQuery = true)
    void updateUrl(String url, UUID idqrcode);
}
