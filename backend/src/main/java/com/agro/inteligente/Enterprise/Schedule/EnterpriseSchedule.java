package com.agro.inteligente.Enterprise.Schedule;

import com.agro.inteligente.Enterprise.Domain.EnterpriseQrCodeDto;
import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Enterprise.UseCase.EnterpriseCase;
import com.agro.inteligente.Utils.Commom.Aws.IStorageAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseSchedule {

    private final IAdapterEnterpriseRepository repository;

    private final IStorageAdapter storageAdapter;

    @Value("${api.aws.bucket-image-qrcode}")
    private String bucket_qrcode;

    private Logger logger = LoggerFactory.getLogger(EnterpriseSchedule.class);
    @Scheduled(cron = "0/30 * * * * *")
    private void ClearQrCodeS3(){
        List<EnterpriseQrCodeDto> lista = this.repository.getAllExpired();

        for (int i = 0; i < lista.stream().count(); i++) {
           if (lista.get(i).getUrl() != ""){
               this.storageAdapter.deleteImage(lista.get(i).getUrl(), bucket_qrcode);
               this.repository.deleteQrCode(UUID.fromString(lista.get(i).getQrcode_id()));
               this.logger.info("QRCODE REMOVIDO POR EXPIRAÇÃO " + lista.get(i).getQrcode_id());
           }
        }
    }
}
