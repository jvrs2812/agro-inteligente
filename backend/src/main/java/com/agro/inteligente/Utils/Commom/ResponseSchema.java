package com.agro.inteligente.Utils.Commom;

import java.util.ArrayList;
import java.util.List;

public class ResponseSchema<T> {
    public ResponseSchema(T data) {
        this.data = data;
        this.error = null;
    }
    public ResponseSchema(List<String> error){
        this.error = error;
        this.data = null;
    }

    public ResponseSchema(T data, List<String>  error){
        this.data = data;
        this.error = error;
    }

    public ResponseSchema(){
        this.data = null;
        this.error = null;
    }

    private T data;
    private List<String> error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String>  getError() {
        return error;
    }

    public void setError(List<String>  error) {
        this.error = error;
    }
}
