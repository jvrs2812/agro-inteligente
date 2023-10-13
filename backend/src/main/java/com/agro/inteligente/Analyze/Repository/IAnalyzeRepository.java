package com.agro.inteligente.Analyze.Repository;

import com.agro.inteligente.Enterprise.Repository.EnterpriseQrCodeModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAnalyzeRepository  extends JpaRepository<AnalyzeModelRepository, UUID> {
}
