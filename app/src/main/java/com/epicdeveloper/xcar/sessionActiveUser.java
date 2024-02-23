package com.epicdeveloper.xcar;

public class sessionActiveUser {
    String active;
    String activeUser;


    public sessionActiveUser(){

    }

    public sessionActiveUser(String activeUser, String active){
        this.activeUser=activeUser;
        this.active=active;

    }

    public String getActiveUser() {
        return activeUser;
    }

    public String getActive() {
        return active;
    }



}
