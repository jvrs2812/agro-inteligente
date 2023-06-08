package com.agro.inteligente.User.Exception;

import com.agro.inteligente.Utils.Commom.Exception.IAgroException;

public enum UserExceptionEnum implements IAgroException {
    CPF_NOT_VALID("cpf informado não é válido", 400),
    EMAIL_EXIST("email informado já cadastrado", 400),
    CPF_EXIST("cpf informado já cadastrado", 400),
    EMAIL_OR_PASSWORD_DONT_EXIST("email ou senha não existe", 404),
    REFRESH_TOKE_WITH_EMAIL_INVALID("refresh token com email inválido", 400),
    REFRESH_TOKEN_INVALIDO("refresh token informado é inválido", 400),
    ID_IS_NOT_VALID("este id não é válido", 400),
    RECOVERY_IS_EXPIRED("esse id de recuperação foi expirado", 401),
    RECOVERY_IS_UTILIZED("esse código já foi utilizado", 401),
    USER_NOT_EXIST("usuário não existe", 404);

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
