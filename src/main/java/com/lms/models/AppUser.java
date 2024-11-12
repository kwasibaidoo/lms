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
    
}
