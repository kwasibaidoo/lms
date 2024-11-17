package com.lms.utils;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AuthUtil {
    // get user role
    private static AuthUtil instance;
    private String userID;
    private String userRole;

    public static AuthUtil getInstance() {
        if (instance == null) {
            instance = new AuthUtil();
        }
        return instance;
    }
}
