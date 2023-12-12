package com.example.ridewizard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static final String EMAIL_PATTERN ="^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_NUMBER_PATTERN = "^\\d{10}$";
    private static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
    public static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    public static boolean validatePhoneNB(final String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
