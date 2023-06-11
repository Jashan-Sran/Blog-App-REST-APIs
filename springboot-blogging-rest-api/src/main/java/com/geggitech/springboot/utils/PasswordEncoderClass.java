package com.geggitech.springboot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderClass {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println( " miller 1223 :  " + passwordEncoder.encode("1234"));
        System.out.println(" jashan admin :  " + passwordEncoder.encode("jashan"));
    }
}
