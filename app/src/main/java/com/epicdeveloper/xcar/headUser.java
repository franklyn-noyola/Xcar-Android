package com.epicdeveloper.xcar;

public class headUser {
    String user_name, user_email,user_password, resetPass;

    public headUser(){

    }

    public headUser(String user_name, String user_email, String user_password, String resetPass){

        this.user_name=user_name;
        this.user_email=user_email;
        this.user_password=user_password;
        this.resetPass=resetPass;

    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email.toLowerCase();
    }

    public String getUser_password() {
        return user_password;
    }
    public String getResetPass() {
        return resetPass;
    }

}
