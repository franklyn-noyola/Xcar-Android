package com.epicdeveloper.xcar.ui;
import com.epicdeveloper.xcar.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginErrorsValidation extends MainActivity {

    public LoginFormValues loginFormValues = new LoginFormValues();

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);



    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean plateValidationFormat(String plate) {
        if (plate.matches("[0-9]{4}?[A-Za-z]{3}") || plate.matches("[A-Za-z]{1}?[0-9]{4}?[A-Za-z]{2}")) {
            return true;
        } else {
            return false;
        }
    }
        public static boolean isValid(final String password) {
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }

    /*    StringBuilder plateN = new StringBuilder();
        StringBuilder plateL = new StringBuilder();
        char c=' ';
        for (int i=0;i<plate.length();i++){
            c=plate.charAt(i);
            if (plateN.length()<4){
                if (Character.isDigit(c)) {
                    plateN.append(c);
                }else{
                    break;
                }
            }
            if (plateN.length()==4){
                    if (!Character.isDigit(c)){
                        plateL.append(c);
                    }
                }
        }
        if (plateN.length()==4 && plateL.length()==3){
            return true;
        }else{
            return false;
        }*/

    public static boolean emailValidation(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    }

