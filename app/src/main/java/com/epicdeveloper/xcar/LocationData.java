package com.epicdeveloper.xcar;

public class LocationData {

    String Date, Type, Longitude, Latitude, Place;

    public LocationData()  {

    }

    public LocationData(String Date, String Type, String Longitude, String Latitude, String Plate ){
        Date = this.Date;
        Type = this.Type;
        Longitude = this.Longitude;
        Latitude = this.Latitude;
        Place = this.Place;
    }

    public String getDate(){
        return Date;
    }

    public String getType(){
        return Type;
    }

    public String getLongitude(){
        return Longitude;
    }

    public String getLatitude(){
        return Latitude;
    }

    public String getPlace(){
        return Place;
    }

}
