package com.agro.inteligente.Analyze.Repository.Adapter;

import com.agro.inteligente.Analyze.Domain.AnalyzeDto;
import com.agro.inteligente.Analyze.Domain.AnalyzeResponseDto;
import com.agro.inteligente.Analyze.Repository.AnalyzeModelRepository;
import com.agro.inteligente.Analyze.Repository.IAnalyzeRepository;
import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.Enterprise.Repository.IEnterpriseRepository;
import com.agro.inteligente.User.Domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyzeAdapter implements IAnalyzeAdapter{

    private final IAdapterEnterpriseRepository enterpriseRepository;

    private final IAnalyzeRepository analyzeModelRepository;

    @Override
    public void save(AnalyzeDto analyzeDto) {
        this.analyzeModelRepository.save(AnalyzeModelRepository.toModel(analyzeDto, EnterpriseModelRepository.toModelDto(this.enterpriseRepository.getMyEnterprise(analyzeDto.getUser_id(), analyzeDto.getEnterprise_id()))));
    }

    @Override
    public List<AnalyzeResponseDto> getMyImages(String enterpriseId) {
        List<AnalyzeResponseDto> lista = new ArrayList<>();

        List<AnalyzeModelRepository> models = this.analyzeModelRepository.getMyImages(UUID.fromString(enterpriseId));

        for (AnalyzeModelRepository model:
        models) {
            lista.add(model.toDto());
        }

        return lista;
    }
}
