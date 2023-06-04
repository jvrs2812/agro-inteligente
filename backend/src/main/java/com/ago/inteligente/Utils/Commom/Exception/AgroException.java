package com.ago.inteligente.Utils.Commom.Exception;

public class AgroException extends Exception{

    private int statusCode;
    public AgroException(IAgroException error){
        super(error.getMsg());

        this.statusCode = error.getStatusCode();
    }

    public String message(){
        return this.getMessage();
    }

    public int statusCode(){
        return this.statusCode;
    }
}
