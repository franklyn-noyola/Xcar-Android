package com.epicdeveloper.allconnected.ui;

import android.widget.EditText;
import android.widget.TextView;

import com.epicdeveloper.allconnected.MainActivity;
import com.epicdeveloper.allconnected.R;


public class LoginFormValues extends MainActivity {
    public EditText plate_user;
    public EditText plate_pass;
    public static String emailSelected;
    int i = 0;
    public static String[] plate_User = {"9887JCX", "8340GSZ", "9143CYF", "0325DCX", "2158HDK", "00001KKK"};
    public static String[] emailUser = {"thefraganc@gmail.com", "franklyn.noyola@gmail.com", "detroyer@gmail.com", "julitosantos@gmail.com", "robertnoyola@gmail.com", "victorg@gmail.com"};
    public static String[] plate_Owner = {"Dalvin Castaños", "Franklyn Garcia", "Miguel Santos", "Julio Santos", "Roberto Noyola", "Victor Gonzalez"};
    public static String[] passWord = {"12345678", "23456789", "34567890", "45678901", "56789012", "67890123"};

    public final String getPlate_Username() {
        String plate_Username = plate_user.getText().toString();
        return plate_Username.toUpperCase();
    }

    public final String getPlate_Password() {
        String plate_Password = plate_pass.getText().toString();
        return plate_Password;
    }

    public final String getPlate_User() {
        String plate_User = plate_Owner[i];
        return plate_User;
    }

    public final Boolean getUserNameValidation() {
        Boolean found = false;

        for (i = 0; i < plate_User.length; i++) {
            if (getPlate_Username().equals(plate_User[i]) && getPlate_Password().equals(passWord[i])) {
                emailSelected = emailUser[i];
                found = true;
                break;
            }
        }
        if (found == true) {
            return true;
        } else {
            return false;

        }
    }
}
