package com.agro.inteligente.Analyze.Domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AnalyzeResponseDto {
    private String url;
}
