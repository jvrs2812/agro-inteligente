package com.agro.inteligente.Enterprise.Exception;

import com.agro.inteligente.Utils.Commom.Exception.IAgroException;

public enum EnterpriseException implements IAgroException {
    CNPJ_NOT_VALID("cnpj informado não é válido", 400),
    CNPJ_ALREADY_EXIST("cnpj informado já cadastrado", 400),
    ID_ENTERPRISE_IS_INVALID("id da empresa informado está incorreto", 400);

    private final String msg;

    private final int statusCode;

    EnterpriseException(String msg, int statusCode) {
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