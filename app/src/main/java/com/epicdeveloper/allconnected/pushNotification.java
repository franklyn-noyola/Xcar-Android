package com.epicdeveloper.allconnected;

public class pushNotification {
    String active, tockenUser;

    public pushNotification(){

    }

    public pushNotification(String active, String tockenUser){
        this.active=active;
        this.tockenUser=tockenUser;
    }

    public String getActive() {
        return active;
    }

    public String getTockenUser() {
        return tockenUser;
    }


}
