package com.geggitech.springboot.service.Impl;

import com.geggitech.springboot.entity.Role;
import com.geggitech.springboot.entity.User;
import com.geggitech.springboot.exception.ResourceFoundInDataBase;
import com.geggitech.springboot.exception.ResourceNotFound;
import com.geggitech.springboot.payload.LoginDto;
import com.geggitech.springboot.payload.RegisterDto;
import com.geggitech.springboot.repository.RoleRepository;
import com.geggitech.springboot.repository.UserRepository;
import com.geggitech.springboot.security.JwtTokenProvider;
import com.geggitech.springboot.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this .userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

      Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

//        check if username exists in DB

        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new ResourceFoundInDataBase("User already exists in DataBase", HttpStatus.BAD_REQUEST);
        }


//        check if email exists in Email

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new ResourceFoundInDataBase("User already exists in DataBase", HttpStatus.BAD_REQUEST);
        }

        User user  = new User();

        user.setUser(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles  = new HashSet<>();
       Role userRole = roleRepository.findByName("ROLE_USER").get();
       roles.add(userRole);
       user.setRoles(roles);

       userRepository.save(user);
        return "User registered successfully";
    }
}
