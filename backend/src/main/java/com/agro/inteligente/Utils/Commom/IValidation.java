package com.agro.inteligente.Utils.Commom;

public interface IValidation {
    boolean isValidCpf(String cpf);

    boolean isValidId(String id);

    boolean isValidCNPJ(String cnpj);

    boolean isValidUUID(String id);
}
