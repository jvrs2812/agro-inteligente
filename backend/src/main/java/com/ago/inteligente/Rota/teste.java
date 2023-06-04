package com.ago.inteligente.Rota;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class teste {
    @GetMapping("ping")
    public String get(){
        return "pong";
    }
}
