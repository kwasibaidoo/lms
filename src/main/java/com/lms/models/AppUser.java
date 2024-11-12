package com.lms.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUser {
    
    private String id;
    private String name;
    private String email;
    private String password;
    private String accountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AppUser(String name, String email, String password, String accountType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

}
