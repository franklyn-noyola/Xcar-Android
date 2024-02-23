package com.epicdeveloper.xcar;

public class languageUserData {
    private String Users;
    private String language;


    public languageUserData(){

    }

    public languageUserData(String Users, String language){
        this.Users=Users;
        this.language = language;
    }

    public String getUsers() {
        return Users;
    }

    public String getLanguage() {
        return language;
    }

}
