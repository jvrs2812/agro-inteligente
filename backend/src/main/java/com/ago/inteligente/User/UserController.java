package com.ago.inteligente.User;

import com.ago.inteligente.User.Domain.UserAuth;
import com.ago.inteligente.User.Domain.UserRefreshToken;
import com.ago.inteligente.User.Domain.UserRegisterDto;
import com.ago.inteligente.User.Domain.UserResponse;
import com.ago.inteligente.User.UseCases.Authentication;
import com.ago.inteligente.Utils.Commom.Exception.AgroException;
import com.ago.inteligente.Utils.Commom.HandleControllerCommom;
import com.ago.inteligente.Utils.Commom.ResponseSchema;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class UserController extends HandleControllerCommom {

    @Autowired
    private Authentication auth;

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
}
