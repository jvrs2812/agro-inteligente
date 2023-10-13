package com.agro.inteligente.Analyze.Repository.Adapter;

import com.agro.inteligente.Analyze.Domain.AnalyzeDto;
import com.agro.inteligente.Analyze.Repository.AnalyzeModelRepository;
import com.agro.inteligente.Analyze.Repository.IAnalyzeRepository;
import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.Enterprise.Repository.IEnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyzeAdapter implements IAnalyzeAdapter{

    private final IAdapterEnterpriseRepository enterpriseRepository;

    private final IAnalyzeRepository analyzeModelRepository;

    @Override
    public void save(AnalyzeDto analyzeDto) {
        this.analyzeModelRepository.save(AnalyzeModelRepository.toModel(analyzeDto, EnterpriseModelRepository.toModelDto(this.enterpriseRepository.getMyEnterprise(analyzeDto.getUser_id(), analyzeDto.getEnterprise_id()))));
    }
}
