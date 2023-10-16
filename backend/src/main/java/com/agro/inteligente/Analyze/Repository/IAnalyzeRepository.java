package com.agro.inteligente.Analyze.Repository;

import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.Enterprise.Repository.EnterpriseQrCodeModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAnalyzeRepository extends JpaRepository<AnalyzeModelRepository, UUID> {

    @Query(value = "select * from analyze_images where enterprise_id = :enterprise_id", nativeQuery = true)
    public List<AnalyzeModelRepository> getMyImages(UUID enterprise_id);
}
