package com.agro.inteligente.Analyze.Repository.Adapter;

import com.agro.inteligente.Analyze.Domain.AnalyzeDto;
import com.agro.inteligente.Analyze.Domain.AnalyzeResponseDto;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Domain.UserResponse;

import java.util.List;

public interface IAnalyzeAdapter {

    void save(AnalyzeDto analyzeDto);

    List<AnalyzeResponseDto> getMyImages(String enterpriseId);
}
