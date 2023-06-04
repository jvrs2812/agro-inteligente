package com.ago.inteligente.Utils.Commom;


import com.ago.inteligente.Utils.Commom.Exception.AgroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class HandleControllerCommom {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseSchema<String> handleValidationException(MethodArgumentNotValidException ex){
        List<String> lista = new ArrayList<String>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            lista.add(error.getDefaultMessage());
        });

        return new ResponseSchema<String>(lista);
    }

    @ExceptionHandler(AgroException.class)
    public ResponseEntity<ResponseSchema<String>> handleAgroException(AgroException except){
        return new ResponseEntity<ResponseSchema<String>>(new ResponseSchema<String>(Collections.singletonList(except.message())), HttpStatus.valueOf(except.statusCode()));
    }

}
