package com.agro.inteligente.Analyze.UseCase;

import com.agro.inteligente.Analyze.Domain.AnalyzeDto;
import com.agro.inteligente.Analyze.Domain.AnalyzeResponseDto;
import com.agro.inteligente.Analyze.Repository.Adapter.IAnalyzeAdapter;
import com.agro.inteligente.Configuration.Security.JwtService;
import com.agro.inteligente.Enterprise.UseCase.EnterpriseValidation;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.Utils.Commom.Aws.IStorageAdapter;
import com.agro.inteligente.Utils.Commom.CustomMultipartFile;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.rabbitmq.client.MessageProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilderSupport;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyzeUseCase {

    private final EnterpriseValidation enterpriseValidation;

    private final JwtService jwtService;

    @Value("${api.aws.bucket-image-fruit}")
    private String bucket_image;

    private final IStorageAdapter storageAdapter;

    private final IAnalyzeAdapter analyzeAdapter;

    private final RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(AnalyzeUseCase.class);

    public List<AnalyzeResponseDto> getMyAnalyzeImage(String enterprise_id)throws AgroException{
        UserDto userLogged = this.jwtService.getUserContextSecurity().toDomain();
        this.enterpriseValidation.isValidEnterprise(enterprise_id, userLogged.getId().toString());

        return this.analyzeAdapter.getMyImages(enterprise_id);
    }

    public AnalyzeResponseDto analyzeImage(String enterprise_id, MultipartFile[] images) throws AgroException, IOException {
        UserDto userLogged = this.jwtService.getUserContextSecurity().toDomain();

        this.enterpriseValidation.isValidEnterprise(enterprise_id, userLogged.getId().toString());

        List<String> urls = this.storageAdapter.saveImage(images, bucket_image);

        Object imageBase64 = rabbitTemplate.convertSendAndReceive("analyze", urls.get(0));

        this.storageAdapter.deleteImage(urls.get(0), bucket_image);

        byte[] bytes = (byte[]) imageBase64;

        String urlSaved = this.storageAdapter.uploadImage(bytes, bucket_image, UUID.randomUUID().toString());

        LocalDateTime dateActually = LocalDateTime.now();
        this.analyzeAdapter.save(new AnalyzeDto(urlSaved, UUID.fromString(enterprise_id), userLogged.getId()));

        return new AnalyzeResponseDto(urlSaved, dateActually);
    }
}
