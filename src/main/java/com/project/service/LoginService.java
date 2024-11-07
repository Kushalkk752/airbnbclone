package com.project.service;

import com.project.entity.AppUser;
import com.project.payload.LoginDto;
import com.project.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public LoginService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUsername.isPresent()){
            AppUser appUser = opUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
                String token = jwtService.generateToken(loginDto.getUsername());
                return token;
            }
            else {
                return null;
            }
        }
        return null;
    }
}
