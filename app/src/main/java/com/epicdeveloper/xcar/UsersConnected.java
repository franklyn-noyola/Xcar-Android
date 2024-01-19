package com.epicdeveloper.xcar;

public class UsersConnected {
    String plate_user,  cartype, carbrand, carmodel,carcolor, year, type, user_name, user_email,user_password, resetPass;;

    public UsersConnected(){

    }

    public UsersConnected(String plate_user, String cartype, String carbrand,
                          String carmodel, String carcolor, String year, String type, String user_name, String user_email, String user_password, String resetPass){

        this.plate_user=plate_user;
        this.cartype=cartype;
        this.carbrand=carbrand;
        this.carmodel=carmodel;
        this.carcolor=carcolor;
        this.year=year;
        this.type=type;
        this.user_email = user_email;
        this.resetPass = resetPass;
        this.user_password = user_password;
        this.user_name = user_name;

    }



    public String getPlate_user() {
        return plate_user;
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



    public String getType() {
        return type;
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
