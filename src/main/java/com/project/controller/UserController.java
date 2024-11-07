package com.project.controller;


import com.project.entity.AppUser;
import com.project.payload.AppUserDto;
import com.project.repository.AppUserRepository;
import com.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    private AppUserRepository appUserRepository;

    public UserController(UserService userService, AppUserRepository appUserRepository) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
    }

    //http://localhost:8080/api/v1/users/signup
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody AppUserDto dto
    ) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(dto.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(4));
        dto.setPassword(encodedPassword);
        dto.setRole("ROLE_USER");
        AppUserDto appUserDto = userService.createUser(dto);
        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createUserOwner(
            @RequestBody AppUserDto dto
    ) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(dto.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(4));
        dto.setPassword(encodedPassword);
        dto.setRole("ROLE_OWNER");
        AppUserDto appUserDto = userService.createUser(dto);
        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppUserDto>> getUsers() {
        List<AppUserDto> appUserDtos = userService.findAllUsers();
        return new ResponseEntity<>(appUserDtos, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @RequestParam Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(
            @PathVariable Long id,
            @RequestBody AppUser appUser) {
        AppUser user = userService.updateUser(id, appUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getUserById(
            @PathVariable Long id) {
        AppUserDto appUserDto = userService.findUserById(id);
        return new ResponseEntity<>(appUserDto,HttpStatus.OK);
    }
}
