package com.epicdeveloper.xcar;

public class UsersConnected {
    String plate_user,  cartype, carbrand, carmodel,carcolor, year, type;

    public UsersConnected(){

    }

    public UsersConnected(String plate_user, String cartype, String carbrand,
                          String carmodel, String carcolor, String year, String type){

        this.plate_user=plate_user;
        this.cartype=cartype;
        this.carbrand=carbrand;
        this.carmodel=carmodel;
        this.carcolor=carcolor;
        this.year=year;
        this.type=type;

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

}
