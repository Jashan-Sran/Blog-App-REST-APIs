package com.geggitech.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceFoundInDataBase extends RuntimeException{

    private String message;
    private HttpStatus code;

    public ResourceFoundInDataBase(String message, HttpStatus code) {
        super(String.format("%s found with %s : '%s'",message,code));
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getCode() {
        return code;
    }
}
