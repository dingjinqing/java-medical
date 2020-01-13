package com.vpu.sql.exception;

public class DuplicateColumnException extends RuntimeException {

    public DuplicateColumnException(String msg){
        super(msg);
    }
}
