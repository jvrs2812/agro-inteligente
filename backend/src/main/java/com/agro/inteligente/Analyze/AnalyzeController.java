package com.agro.inteligente.Analyze;

import com.agro.inteligente.Analyze.Domain.AnalyzeResponseDto;
import com.agro.inteligente.Analyze.UseCase.AnalyzeUseCase;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.HandleControllerCommom;
import com.agro.inteligente.Utils.Commom.ResponseSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analyze")
@RequiredArgsConstructor
public class AnalyzeController extends HandleControllerCommom {

    private final AnalyzeUseCase analyzeUseCase;

    @PostMapping(value = "{id_enterprise}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseSchema<AnalyzeResponseDto>> postImage(@PathVariable("id_enterprise") String id_enterprise, @RequestParam MultipartFile[] images) throws AgroException, IOException {
        return new ResponseEntity(new ResponseSchema<AnalyzeResponseDto>(this.analyzeUseCase.analyzeImage(id_enterprise, images)), HttpStatus.OK);
    }

    @GetMapping(value = "my-images/{id_enterprise}")
    public ResponseEntity<ResponseSchema<List<AnalyzeResponseDto>>> getMyImages(@PathVariable("id_enterprise") String id_enterprise)throws AgroException{
        return new ResponseEntity(new ResponseSchema<List<AnalyzeResponseDto>>(this.analyzeUseCase.getMyAnalyzeImage(id_enterprise)), HttpStatus.OK);
    }


}
