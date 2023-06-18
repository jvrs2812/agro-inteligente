package com.agro.inteligente.Enterprise;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.UseCase.EnterpriseCase;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.HandleControllerCommom;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class EnterpriseController extends HandleControllerCommom {

    private final EnterpriseCase enterpriseCase;

    @PostMapping("enterprise")
    public ResponseEntity createEnterprise(@Valid @RequestBody EnterpriseDto enterpriseDto) throws AgroException {
        this.enterpriseCase.CreateEnterprise(enterpriseDto);
        return new ResponseEntity(null, HttpStatus.OK);
    }

}
