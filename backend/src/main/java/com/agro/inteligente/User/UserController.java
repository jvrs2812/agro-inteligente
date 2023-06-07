package com.agro.inteligente.User;

import com.agro.inteligente.User.Domain.*;
import com.agro.inteligente.User.UseCases.Authentication;
import com.agro.inteligente.User.UseCases.Recovery;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.HandleControllerCommom;
import com.agro.inteligente.Utils.Commom.ResponseSchema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController extends HandleControllerCommom {

    private Authentication auth;

    private final Recovery recovery;

    @PostMapping("/register")
    public ResponseEntity<ResponseSchema<UserResponse>> register(@Valid @RequestBody UserRegisterDto userRegisterDto) throws AgroException {
        return new ResponseEntity(new ResponseSchema<UserResponse>(this.auth.Register(userRegisterDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseSchema<UserResponse>> login(@Valid @RequestBody UserAuth userAuth) throws AgroException {
        return new ResponseEntity(new ResponseSchema<UserResponse>(this.auth.Login(userAuth)), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseSchema<UserResponse>> refreshToken(@Valid @RequestBody UserRefreshToken refreshToken) throws AgroException {
        return new ResponseEntity(new ResponseSchema<UserResponse>(this.auth.refreshToken(refreshToken)), HttpStatus.OK);
    }

    @PostMapping("/recovery-password")
    public ResponseEntity recoveryPassword(@Valid @RequestBody UserRecoveryPassword recoveryPassword){
        this.recovery.recoveryPasswordWithEmail(recoveryPassword);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
