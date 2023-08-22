package com.epicdeveloper.xcar;

public class ActivatedUser {
    String activated, userActivated;
    String activationCode;

    public ActivatedUser(){

    }

    public ActivatedUser(String activated, String userActivated){
        this.activated=activated;
        this.userActivated=userActivated;

    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getUserActivated() {
        return userActivated;
    }

    public void setUserActivated(String userActivated) {
        this.userActivated = userActivated;
    }



}
