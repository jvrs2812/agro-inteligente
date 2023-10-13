package com.agro.inteligente.Analyze.Repository;

import com.agro.inteligente.Analyze.Domain.AnalyzeDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.Enterprise.Repository.EnterpriseModelRepository;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "analyze_images")
@Entity
public class AnalyzeModelRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  updatable = false, unique = true, nullable = false)
    private UUID id;
    @NotNull
    private String url_image;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_analyze;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private EnterpriseModelRepository enterprise;

    public static AnalyzeModelRepository toModel(AnalyzeDto analyzeDto, EnterpriseModelRepository enterprise){
        AnalyzeModelRepository analyzeModelRepository = new AnalyzeModelRepository();
        analyzeModelRepository.id = UUID.randomUUID();

        LocalDateTime dateActually = LocalDateTime.now();

        analyzeModelRepository.date_analyze = Date.from(dateActually.atZone(ZoneId.systemDefault()).toInstant());

        analyzeModelRepository.url_image = analyzeDto.getUrl();

        analyzeModelRepository.enterprise = enterprise;

        return analyzeModelRepository;
    }
}
