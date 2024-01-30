package com.epicdeveloper.xcar;

public class locationHistoryList {
    private String date;
    private String latitude;
    private String longitude;
    private String place;

    public locationHistoryList(String date, String latitude, String longitude, String place){
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
    }

    public locationHistoryList(){

    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getLatiude(){
        return latitude;
    }

    public String getLatitude(){
        return latitude;
    }
    public void setlLatitude(String latitude){
        this.latitude = latitude;
    }
    public String getLongitude(){
        return longitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getPlace(){
        return place;
    }

    public void setPlace(String place){
        this.place = place;
    }



}
