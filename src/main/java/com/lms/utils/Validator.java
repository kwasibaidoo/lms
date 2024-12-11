package com.lms.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lms.config.DatabaseConfig;

public class Validator {
    private DatabaseConfig databaseConfig = new DatabaseConfig();
    public ValidationResult validate(String value, String... args) {

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
            // eg.
            else if(rule.startsWith("unique")){
                System.out.println("HJEREEEEEE KOJSDFDSG SFGIOHSFKGHKSFGH \n \n \n");
                String column = rule.substring(rule.indexOf(",") + 1);
                String table = rule.substring(7,rule.indexOf(","));
                String sql = String.format("SELECT COUNT(*) from %s WHERE %s=?", table, column);
                try (Connection connection = databaseConfig.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                    preparedStatement.setString(1, value);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        if(resultSet.getInt(1) != 0) {
                            return new ValidationResult("Value is already taken", false);
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

    public ValidationResult passwordValidation(String value, String confirmation) {
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

    public ValidationResult validateDate(String value, String... args) {
        // Define the expected date format (e.g., "yyyy-MM-dd")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Parse the input date
            LocalDate inputDate = LocalDate.parse(value, formatter);
            LocalDate currentDate = LocalDate.now();

            for (String rule : args) {
                if (rule.equals("not_null") && value.equals("")) {
                    return new ValidationResult("Required field", false);
                } else if (rule.equals("valid_date")) {
                    // Date format is already validated by LocalDate.parse
                } else if (rule.equals("before_today")) {
                    if (!inputDate.isBefore(currentDate)) {
                        return new ValidationResult("Date must be before today", false);
                    }
                } else if (rule.equals("after_today")) {
                    if (!inputDate.isAfter(currentDate)) {
                        return new ValidationResult("Date must be after today", false);
                    }
                }
            }
        } catch (DateTimeParseException e) {
            return new ValidationResult("Invalid date format. Expected format: yyyy-MM-dd", false);
        }

        return new ValidationResult();
    }
}
