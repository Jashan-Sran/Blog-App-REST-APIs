package com.geggitech.springboot.exception;

import com.geggitech.springboot.payload.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetail> errorDetailResponseEntity(ResourceNotFound resourceNotFound,
                                                                 WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(),resourceNotFound.getMessage()
                ,webRequest.getDescription(false), HttpStatus.NOT_FOUND);

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ResourceFoundInDataBase.class)
    public ResponseEntity<ErrorDetail> errorDetailResponseEntity(ResourceFoundInDataBase resourceFoundInDataBase,
                                                                 WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(),resourceFoundInDataBase.getMessage()
                ,webRequest.getDescription(false), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(),exception.getMessage()
                ,webRequest.getDescription(false),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{
            String filedName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(filedName,message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail> errorAccessDeniedException(AccessDeniedException resourceNotFound,
                                                                 WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(),resourceNotFound.getMessage()
                ,webRequest.getDescription(false), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.UNAUTHORIZED);

    }
}
