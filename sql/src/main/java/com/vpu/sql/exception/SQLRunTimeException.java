package com.vpu.sql.exception;

public class SQLRunTimeException extends RuntimeException{

    public SQLRunTimeException(String errorMessage){
        super(errorMessage);
    }
}
