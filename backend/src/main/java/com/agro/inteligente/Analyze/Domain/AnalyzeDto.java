package com.agro.inteligente.Analyze.Domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class AnalyzeDto {
    private String url;

    private UUID enterprise_id;

    private UUID user_id;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(UUID enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public AnalyzeDto(String url, UUID enterprise_id, UUID user_id) {
        this.url = url;
        this.enterprise_id = enterprise_id;
        this.user_id = user_id;
    }
}
