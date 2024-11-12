package com.lms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validate(String value, String... args) {

        for (String rule : args) {
            if(rule == "not_null"){
                if(value == ""){
                    return false;
                }
            }
            else if(rule == "email") {
                Pattern pattern = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
                Matcher matcher = pattern.matcher(value);

                if(!matcher.find(0)){
                    return false;
                }
            }
        }
        // not null
        // only alphabets
        // email
        // password
        // max
        // min
        return true;
    }
}
