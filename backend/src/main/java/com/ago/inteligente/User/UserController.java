package com.ago.inteligente.User;

import com.ago.inteligente.User.Domain.UserAuth;
import com.ago.inteligente.User.Domain.UserRegisterDto;
import com.ago.inteligente.User.UseCases.UserResgister;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserResgister register;

    @PostMapping("v1/users")
    public ResponseEntity getUser(@Valid @RequestBody UserRegisterDto userRegisterDto){

        this.register.Register(userRegisterDto);

        return new ResponseEntity(null, HttpStatus.OK);
    }
}
