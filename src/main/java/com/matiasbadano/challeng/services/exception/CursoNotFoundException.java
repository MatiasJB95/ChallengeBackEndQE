package com.matiasbadano.challeng.services.exception;

public class CursoNotFoundException extends RuntimeException{
    public CursoNotFoundException(String message) {
        super(message);
    }
}

