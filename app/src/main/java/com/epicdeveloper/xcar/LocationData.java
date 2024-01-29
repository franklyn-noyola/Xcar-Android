package com.epicdeveloper.xcar;

public class LocationData {

    String date, type, place;
    double longitude, latitude;

    public LocationData()  {

    }

    public LocationData(String date, String type, double longitude, double latitude, String place){
        this.date=date;
        this.type=type;
        this.longitude=longitude;
        this.latitude=latitude;
        this.place=place;
    }

    public String getDate(){
        return date;
    }

    public String getType(){
        return type;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getPlace(){
        return place;
    }

}
