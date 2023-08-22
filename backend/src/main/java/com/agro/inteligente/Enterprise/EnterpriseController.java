package com.agro.inteligente.Enterprise;

import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.Enterprise.UseCase.EnterpriseCase;
import com.agro.inteligente.User.Domain.UserResponse;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.HandleControllerCommom;
import com.agro.inteligente.Utils.Commom.ResponseSchema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("my-enterprise")
    public ResponseEntity<ResponseSchema<List<EnterpriseResponseDto>>> getMyEnterprise(){
        return new ResponseEntity(new ResponseSchema<List<EnterpriseResponseDto>>(this.enterpriseCase.getMyEnterprise()), HttpStatus.OK);
    }

}
