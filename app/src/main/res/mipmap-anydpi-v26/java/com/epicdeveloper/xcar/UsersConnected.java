package com.epicdeveloper.xcar;

public class UsersConnected {
    String user_name, user_email,plate_user,  user_password, phone, cartype, carbrand, carmodel,carcolor, year, resetPass;

    public UsersConnected(){

    }

    public UsersConnected(String user_name, String plate_user, String user_email, String user_password, String cartype, String carbrand,
                          String carmodel, String carcolor, String year, String resetPass){

        this.user_name=user_name;
        this.plate_user=plate_user;
        this.user_email=user_email;
        this.user_password=user_password;
        this.cartype=cartype;
        this.carbrand=carbrand;
        this.carmodel=carmodel;
        this.carcolor=carcolor;
        this.year=year;
        this.resetPass=resetPass;

    }

    public String getUser_name() {
        return user_name;
    }

    public String getPlate_user() {
        return plate_user;
    }

    public String getUser_email() {
        return user_email.toLowerCase();
    }

    public String getUser_password() {
        return user_password;
    }

    public String getCartype() {
        return cartype;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public String getCarcolor() {
        return carcolor;
    }

    public String getYear() {
        return year;
    }

    public String getResetPass() {
        return resetPass;
    }




}
