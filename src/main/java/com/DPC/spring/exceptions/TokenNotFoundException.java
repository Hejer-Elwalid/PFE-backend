package com.DPC.spring.exceptions;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(String message){
        super(message);
    }
}
