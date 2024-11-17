package com.lms.models;

import java.sql.Timestamp;

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
    private Timestamp deletedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public AppUser(String name, String email, String password, String accountType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public AppUser(String id, String name, String email, String accountType, Timestamp deletedAt, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.accountType = accountType;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
