package com.project.payload;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserDto {
    private String username;
    private String email;
    private String name;
    private String password;
    private String role;
}
