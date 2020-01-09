package com.vpu.sql.exception;

public class DuplicateIndexException extends RuntimeException{

    public DuplicateIndexException(String msg){
        super(msg);
    }
}
