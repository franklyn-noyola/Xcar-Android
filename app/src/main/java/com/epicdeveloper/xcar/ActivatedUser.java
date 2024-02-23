package com.epicdeveloper.xcar;

public class ActivatedUser {
    String activated, userActivated, emailActivated;
    String activationCode;

    public ActivatedUser(){

    }

    public ActivatedUser(String activated, String userActivated,String  emailActivated){
        this.activated=activated;
        this.userActivated=userActivated;
        this.emailActivated=emailActivated;


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

    public String getEmailActivated() {return emailActivated;}

    public void setEmailActivated(String emailActivated) {
        this.emailActivated = emailActivated;
    }



}
