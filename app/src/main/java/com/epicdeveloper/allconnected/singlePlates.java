package com.epicdeveloper.xcar;

public class singlePlates {
    String plate_id,  cartype, carbrand, carmodel,carcolor, year;

    public singlePlates(){

    }

    public singlePlates(String plate_id, String cartype, String carbrand,
                        String carmodel, String carcolor, String year){

        this.plate_id=plate_id;
        this.cartype=cartype;
        this.carbrand=carbrand;
        this.carmodel=carmodel;
        this.carcolor=carcolor;
        this.year=year;
    }



    public String getPlate_id() {
        return plate_id;
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

}
