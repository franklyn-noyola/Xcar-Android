package com.epicdeveloper.xcar.ui.Perfil;


public class profileData {

    private static String phoneData;
    private static String carTypeData;
    private static String carBrandData;
    private static String carModelData;
    private static String carColorData;
    private static String carYearData;
    private static String newPassData;


    public void profileData(){

    }

    public void profileData(String phoneData, String carTypeData, String carBrandData, String carModelData, String carColorData, String carYearData, String newPassData){
        this.phoneData=phoneData;
        this.carTypeData=carTypeData;
        this.carBrandData=carBrandData;
        this.carModelData=carModelData;
        this.carColorData=carColorData;
        this.carYearData=carYearData;
        this.newPassData=newPassData;

}


    public String getPhoneData() {
        return phoneData;
    }

    public String getCarTypeData() {
        return carTypeData;
    }

    public String getCarBrandData() {
        return carBrandData;
    }

    public String getCarModelData() {
        return carModelData;
    }

    public String getCarColorData() {
        return carColorData;
    }

    public  String getCarYearData() {
        return carYearData;
    }

    public String getNewPassData() {
        return newPassData;
    }


}



