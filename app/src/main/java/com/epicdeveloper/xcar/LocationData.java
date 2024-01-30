package com.epicdeveloper.xcar;

public class LocationData {

    String date, type, place, longitude, latitude;

    public LocationData()  {

    }

    public LocationData(String date, String type, String longitude, String latitude, String place){
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

    public String getLongitude(){
        return longitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getPlace(){
        return place;
    }

}
