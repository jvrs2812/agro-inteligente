package com.ago.inteligente.User.Exception;

import com.ago.inteligente.Utils.Commom.Exception.IAgroException;

public enum UserExceptionEnum implements IAgroException {
    CPF_NOT_VALID("cpf informado não é válido", 400),
    EMAIL_EXIST("email informado já cadastrado", 400),
    CPF_EXIST("cpf informado já cadastrado", 400),
    EMAIL_OR_PASSWORD_DONT_EXIST("email ou senha não existe", 404),
    REFRESH_TOKE_WITH_EMAIL_INVALID("refresh token com email inválido", 400),
    REFRESH_TOKEN_INVALIDO("refresh token informado é inválido", 400);

    private final String msg;

    private final int statusCode;

    UserExceptionEnum(String msg, int statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }
}
