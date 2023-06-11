package com.geggitech.springboot.payload;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetail {

    private Date timeStamp;
    private String message;
    private String details;
    private HttpStatus errorCode;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public ErrorDetail(Date timeStamp, String message, String details, HttpStatus errorCode) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }
}
