package com.ago.inteligente.Token.Exception;

import com.ago.inteligente.Utils.Commom.Exception.IAgroException;

public enum TokenExceptionEnum implements IAgroException {
    TOKEN_MULTIPLE_GENERATE("tentativa de gerar token multiplas vezes", 429);

    private final String msg;

    private final int statusCode;

    TokenExceptionEnum(String msg, int statusCode){
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
