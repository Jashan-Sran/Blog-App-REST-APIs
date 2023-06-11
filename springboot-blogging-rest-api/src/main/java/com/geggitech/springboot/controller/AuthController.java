package com.geggitech.springboot.controller;

import com.geggitech.springboot.payload.JWTAuthResponse;
import com.geggitech.springboot.payload.LoginDto;
import com.geggitech.springboot.payload.RegisterDto;
import com.geggitech.springboot.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String response = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

        jwtAuthResponse.setAccessToken(response);

        return  ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
