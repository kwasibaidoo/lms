package com.lms.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lms.config.DatabaseConfig;

public class Validator {
    public static ValidationResult validate(String value, String... args) {

        for (String rule : args) {
            if(rule.equals("not_null")){
                if(value.equals("")){
                    return new ValidationResult("Required field", false);
                }
            }
            else if(rule.equals("email")) {
                Pattern pattern = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(value);

                if(!matcher.find()){
                    return new ValidationResult("Field must be a valid email", false);
                }
            }
            else if(rule.startsWith("unique")){
                String column = rule.substring(7);
                String sql = String.format("SELECT COUNT(*) from users WHERE email=?", column);
                try (Connection connection = DatabaseConfig.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                    preparedStatement.setString(1, value);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        if(resultSet.getInt(1) != 0) {
                            return new ValidationResult("Email is already taken", false);
                        }
                    }
                    
                } catch (Exception e) {

                }
            }
            else {
                return new ValidationResult("", true);
            }
        }
        return new ValidationResult();
        // not null
        // only alphabets
        // email
        // password
        // max
        // min
    }

    public static ValidationResult passwordValidation(String value, String confirmation) {
        if(!value.equals(confirmation)) {
            return new ValidationResult("Passwords do not match",false);
        }
        else {
            if(value.length() < 8) {
                return new ValidationResult("Password should be more than 8 characters", false);
            }
        }
        return new ValidationResult();
    }
}
