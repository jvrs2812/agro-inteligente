package com.agro.inteligente.Analyze.Domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AnalyzeResponseDto {
    private String url;

    private LocalDateTime date_analyze;

    public AnalyzeResponseDto(String url, LocalDateTime date_analyze) {
        this.url = url;
        this.date_analyze = date_analyze;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDate_analyze() {
        return date_analyze;
    }

    public void setDate_analyze(LocalDateTime date_analyze) {
        this.date_analyze = date_analyze;
    }
}
