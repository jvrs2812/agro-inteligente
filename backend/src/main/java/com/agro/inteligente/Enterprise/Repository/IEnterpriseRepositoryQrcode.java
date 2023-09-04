package com.agro.inteligente.Enterprise.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IEnterpriseRepositoryQrcode extends JpaRepository<EnterpriseQrCodeModelRepository, UUID> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update enterprise_qrcode_invite set url = :url where id =:idqrcode", nativeQuery = true)
    void updateUrl(String url, UUID idqrcode);

    @Query(value = "select * from enterprise_qrcode_invite where expired_at <:data ", nativeQuery = true)
    List<EnterpriseQrCodeModelRepository> getAllExpired(Date data);
}
