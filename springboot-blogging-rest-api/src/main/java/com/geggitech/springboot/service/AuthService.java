package com.geggitech.springboot.service;

import com.geggitech.springboot.payload.LoginDto;
import com.geggitech.springboot.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
